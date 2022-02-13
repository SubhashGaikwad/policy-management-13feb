package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.NomineeRepository;
import com.mycompany.myapp.service.NomineeQueryService;
import com.mycompany.myapp.service.NomineeService;
import com.mycompany.myapp.service.criteria.NomineeCriteria;
import com.mycompany.myapp.service.dto.NomineeDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Nominee}.
 */
@RestController
@RequestMapping("/api")
public class NomineeResource {

    private final Logger log = LoggerFactory.getLogger(NomineeResource.class);

    private static final String ENTITY_NAME = "nominee";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NomineeService nomineeService;

    private final NomineeRepository nomineeRepository;

    private final NomineeQueryService nomineeQueryService;

    public NomineeResource(NomineeService nomineeService, NomineeRepository nomineeRepository, NomineeQueryService nomineeQueryService) {
        this.nomineeService = nomineeService;
        this.nomineeRepository = nomineeRepository;
        this.nomineeQueryService = nomineeQueryService;
    }

    /**
     * {@code POST  /nominees} : Create a new nominee.
     *
     * @param nomineeDTO the nomineeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nomineeDTO, or with status {@code 400 (Bad Request)} if the nominee has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nominees")
    public ResponseEntity<NomineeDTO> createNominee(@Valid @RequestBody NomineeDTO nomineeDTO) throws URISyntaxException {
        log.debug("REST request to save Nominee : {}", nomineeDTO);
        if (nomineeDTO.getId() != null) {
            throw new BadRequestAlertException("A new nominee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NomineeDTO result = nomineeService.save(nomineeDTO);
        return ResponseEntity
            .created(new URI("/api/nominees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nominees/:id} : Updates an existing nominee.
     *
     * @param id the id of the nomineeDTO to save.
     * @param nomineeDTO the nomineeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nomineeDTO,
     * or with status {@code 400 (Bad Request)} if the nomineeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nomineeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nominees/{id}")
    public ResponseEntity<NomineeDTO> updateNominee(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NomineeDTO nomineeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Nominee : {}, {}", id, nomineeDTO);
        if (nomineeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nomineeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nomineeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NomineeDTO result = nomineeService.save(nomineeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nomineeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nominees/:id} : Partial updates given fields of an existing nominee, field will ignore if it is null
     *
     * @param id the id of the nomineeDTO to save.
     * @param nomineeDTO the nomineeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nomineeDTO,
     * or with status {@code 400 (Bad Request)} if the nomineeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the nomineeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the nomineeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nominees/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NomineeDTO> partialUpdateNominee(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NomineeDTO nomineeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Nominee partially : {}, {}", id, nomineeDTO);
        if (nomineeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nomineeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nomineeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NomineeDTO> result = nomineeService.partialUpdate(nomineeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nomineeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /nominees} : get all the nominees.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nominees in body.
     */
    @GetMapping("/nominees")
    public ResponseEntity<List<NomineeDTO>> getAllNominees(
        NomineeCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Nominees by criteria: {}", criteria);
        Page<NomineeDTO> page = nomineeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nominees/count} : count all the nominees.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/nominees/count")
    public ResponseEntity<Long> countNominees(NomineeCriteria criteria) {
        log.debug("REST request to count Nominees by criteria: {}", criteria);
        return ResponseEntity.ok().body(nomineeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /nominees/:id} : get the "id" nominee.
     *
     * @param id the id of the nomineeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nomineeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nominees/{id}")
    public ResponseEntity<NomineeDTO> getNominee(@PathVariable Long id) {
        log.debug("REST request to get Nominee : {}", id);
        Optional<NomineeDTO> nomineeDTO = nomineeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nomineeDTO);
    }

    /**
     * {@code DELETE  /nominees/:id} : delete the "id" nominee.
     *
     * @param id the id of the nomineeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nominees/{id}")
    public ResponseEntity<Void> deleteNominee(@PathVariable Long id) {
        log.debug("REST request to delete Nominee : {}", id);
        nomineeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
