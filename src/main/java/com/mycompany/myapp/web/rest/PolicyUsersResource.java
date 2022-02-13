package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.PolicyUsersRepository;
import com.mycompany.myapp.service.PolicyUsersQueryService;
import com.mycompany.myapp.service.PolicyUsersService;
import com.mycompany.myapp.service.criteria.PolicyUsersCriteria;
import com.mycompany.myapp.service.dto.PolicyUsersDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.PolicyUsers}.
 */
@RestController
@RequestMapping("/api")
public class PolicyUsersResource {

    private final Logger log = LoggerFactory.getLogger(PolicyUsersResource.class);

    private static final String ENTITY_NAME = "policyUsers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PolicyUsersService policyUsersService;

    private final PolicyUsersRepository policyUsersRepository;

    private final PolicyUsersQueryService policyUsersQueryService;

    public PolicyUsersResource(
        PolicyUsersService policyUsersService,
        PolicyUsersRepository policyUsersRepository,
        PolicyUsersQueryService policyUsersQueryService
    ) {
        this.policyUsersService = policyUsersService;
        this.policyUsersRepository = policyUsersRepository;
        this.policyUsersQueryService = policyUsersQueryService;
    }

    /**
     * {@code POST  /policy-users} : Create a new policyUsers.
     *
     * @param policyUsersDTO the policyUsersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new policyUsersDTO, or with status {@code 400 (Bad Request)} if the policyUsers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/policy-users")
    public ResponseEntity<PolicyUsersDTO> createPolicyUsers(@Valid @RequestBody PolicyUsersDTO policyUsersDTO) throws URISyntaxException {
        log.debug("REST request to save PolicyUsers : {}", policyUsersDTO);
        if (policyUsersDTO.getId() != null) {
            throw new BadRequestAlertException("A new policyUsers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PolicyUsersDTO result = policyUsersService.save(policyUsersDTO);
        return ResponseEntity
            .created(new URI("/api/policy-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /policy-users/:id} : Updates an existing policyUsers.
     *
     * @param id the id of the policyUsersDTO to save.
     * @param policyUsersDTO the policyUsersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated policyUsersDTO,
     * or with status {@code 400 (Bad Request)} if the policyUsersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the policyUsersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/policy-users/{id}")
    public ResponseEntity<PolicyUsersDTO> updatePolicyUsers(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PolicyUsersDTO policyUsersDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PolicyUsers : {}, {}", id, policyUsersDTO);
        if (policyUsersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, policyUsersDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!policyUsersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PolicyUsersDTO result = policyUsersService.save(policyUsersDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, policyUsersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /policy-users/:id} : Partial updates given fields of an existing policyUsers, field will ignore if it is null
     *
     * @param id the id of the policyUsersDTO to save.
     * @param policyUsersDTO the policyUsersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated policyUsersDTO,
     * or with status {@code 400 (Bad Request)} if the policyUsersDTO is not valid,
     * or with status {@code 404 (Not Found)} if the policyUsersDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the policyUsersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/policy-users/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PolicyUsersDTO> partialUpdatePolicyUsers(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PolicyUsersDTO policyUsersDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PolicyUsers partially : {}, {}", id, policyUsersDTO);
        if (policyUsersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, policyUsersDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!policyUsersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PolicyUsersDTO> result = policyUsersService.partialUpdate(policyUsersDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, policyUsersDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /policy-users} : get all the policyUsers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of policyUsers in body.
     */
    @GetMapping("/policy-users")
    public ResponseEntity<List<PolicyUsersDTO>> getAllPolicyUsers(
        PolicyUsersCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PolicyUsers by criteria: {}", criteria);
        Page<PolicyUsersDTO> page = policyUsersQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /policy-users/count} : count all the policyUsers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/policy-users/count")
    public ResponseEntity<Long> countPolicyUsers(PolicyUsersCriteria criteria) {
        log.debug("REST request to count PolicyUsers by criteria: {}", criteria);
        return ResponseEntity.ok().body(policyUsersQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /policy-users/:id} : get the "id" policyUsers.
     *
     * @param id the id of the policyUsersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the policyUsersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/policy-users/{id}")
    public ResponseEntity<PolicyUsersDTO> getPolicyUsers(@PathVariable Long id) {
        log.debug("REST request to get PolicyUsers : {}", id);
        Optional<PolicyUsersDTO> policyUsersDTO = policyUsersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(policyUsersDTO);
    }

    /**
     * {@code DELETE  /policy-users/:id} : delete the "id" policyUsers.
     *
     * @param id the id of the policyUsersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/policy-users/{id}")
    public ResponseEntity<Void> deletePolicyUsers(@PathVariable Long id) {
        log.debug("REST request to delete PolicyUsers : {}", id);
        policyUsersService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
