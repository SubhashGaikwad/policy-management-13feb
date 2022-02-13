package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.UsersTypeRepository;
import com.mycompany.myapp.service.UsersTypeQueryService;
import com.mycompany.myapp.service.UsersTypeService;
import com.mycompany.myapp.service.criteria.UsersTypeCriteria;
import com.mycompany.myapp.service.dto.UsersTypeDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.UsersType}.
 */
@RestController
@RequestMapping("/api")
public class UsersTypeResource {

    private final Logger log = LoggerFactory.getLogger(UsersTypeResource.class);

    private static final String ENTITY_NAME = "usersType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UsersTypeService usersTypeService;

    private final UsersTypeRepository usersTypeRepository;

    private final UsersTypeQueryService usersTypeQueryService;

    public UsersTypeResource(
        UsersTypeService usersTypeService,
        UsersTypeRepository usersTypeRepository,
        UsersTypeQueryService usersTypeQueryService
    ) {
        this.usersTypeService = usersTypeService;
        this.usersTypeRepository = usersTypeRepository;
        this.usersTypeQueryService = usersTypeQueryService;
    }

    /**
     * {@code POST  /users-types} : Create a new usersType.
     *
     * @param usersTypeDTO the usersTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new usersTypeDTO, or with status {@code 400 (Bad Request)} if the usersType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/users-types")
    public ResponseEntity<UsersTypeDTO> createUsersType(@Valid @RequestBody UsersTypeDTO usersTypeDTO) throws URISyntaxException {
        log.debug("REST request to save UsersType : {}", usersTypeDTO);
        if (usersTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new usersType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UsersTypeDTO result = usersTypeService.save(usersTypeDTO);
        return ResponseEntity
            .created(new URI("/api/users-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /users-types/:id} : Updates an existing usersType.
     *
     * @param id the id of the usersTypeDTO to save.
     * @param usersTypeDTO the usersTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated usersTypeDTO,
     * or with status {@code 400 (Bad Request)} if the usersTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the usersTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/users-types/{id}")
    public ResponseEntity<UsersTypeDTO> updateUsersType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UsersTypeDTO usersTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update UsersType : {}, {}", id, usersTypeDTO);
        if (usersTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, usersTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!usersTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UsersTypeDTO result = usersTypeService.save(usersTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, usersTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /users-types/:id} : Partial updates given fields of an existing usersType, field will ignore if it is null
     *
     * @param id the id of the usersTypeDTO to save.
     * @param usersTypeDTO the usersTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated usersTypeDTO,
     * or with status {@code 400 (Bad Request)} if the usersTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the usersTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the usersTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/users-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UsersTypeDTO> partialUpdateUsersType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UsersTypeDTO usersTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update UsersType partially : {}, {}", id, usersTypeDTO);
        if (usersTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, usersTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!usersTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UsersTypeDTO> result = usersTypeService.partialUpdate(usersTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, usersTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /users-types} : get all the usersTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of usersTypes in body.
     */
    @GetMapping("/users-types")
    public ResponseEntity<List<UsersTypeDTO>> getAllUsersTypes(
        UsersTypeCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get UsersTypes by criteria: {}", criteria);
        Page<UsersTypeDTO> page = usersTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /users-types/count} : count all the usersTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/users-types/count")
    public ResponseEntity<Long> countUsersTypes(UsersTypeCriteria criteria) {
        log.debug("REST request to count UsersTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(usersTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /users-types/:id} : get the "id" usersType.
     *
     * @param id the id of the usersTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the usersTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/users-types/{id}")
    public ResponseEntity<UsersTypeDTO> getUsersType(@PathVariable Long id) {
        log.debug("REST request to get UsersType : {}", id);
        Optional<UsersTypeDTO> usersTypeDTO = usersTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(usersTypeDTO);
    }

    /**
     * {@code DELETE  /users-types/:id} : delete the "id" usersType.
     *
     * @param id the id of the usersTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/users-types/{id}")
    public ResponseEntity<Void> deleteUsersType(@PathVariable Long id) {
        log.debug("REST request to delete UsersType : {}", id);
        usersTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
