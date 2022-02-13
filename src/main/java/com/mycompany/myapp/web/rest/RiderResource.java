package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.RiderRepository;
import com.mycompany.myapp.service.RiderQueryService;
import com.mycompany.myapp.service.RiderService;
import com.mycompany.myapp.service.criteria.RiderCriteria;
import com.mycompany.myapp.service.dto.RiderDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Rider}.
 */
@RestController
@RequestMapping("/api")
public class RiderResource {

    private final Logger log = LoggerFactory.getLogger(RiderResource.class);

    private static final String ENTITY_NAME = "rider";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RiderService riderService;

    private final RiderRepository riderRepository;

    private final RiderQueryService riderQueryService;

    public RiderResource(RiderService riderService, RiderRepository riderRepository, RiderQueryService riderQueryService) {
        this.riderService = riderService;
        this.riderRepository = riderRepository;
        this.riderQueryService = riderQueryService;
    }

    /**
     * {@code POST  /riders} : Create a new rider.
     *
     * @param riderDTO the riderDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new riderDTO, or with status {@code 400 (Bad Request)} if the rider has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/riders")
    public ResponseEntity<RiderDTO> createRider(@Valid @RequestBody RiderDTO riderDTO) throws URISyntaxException {
        log.debug("REST request to save Rider : {}", riderDTO);
        if (riderDTO.getId() != null) {
            throw new BadRequestAlertException("A new rider cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RiderDTO result = riderService.save(riderDTO);
        return ResponseEntity
            .created(new URI("/api/riders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /riders/:id} : Updates an existing rider.
     *
     * @param id the id of the riderDTO to save.
     * @param riderDTO the riderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated riderDTO,
     * or with status {@code 400 (Bad Request)} if the riderDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the riderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/riders/{id}")
    public ResponseEntity<RiderDTO> updateRider(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RiderDTO riderDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Rider : {}, {}", id, riderDTO);
        if (riderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, riderDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!riderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RiderDTO result = riderService.save(riderDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, riderDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /riders/:id} : Partial updates given fields of an existing rider, field will ignore if it is null
     *
     * @param id the id of the riderDTO to save.
     * @param riderDTO the riderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated riderDTO,
     * or with status {@code 400 (Bad Request)} if the riderDTO is not valid,
     * or with status {@code 404 (Not Found)} if the riderDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the riderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/riders/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RiderDTO> partialUpdateRider(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RiderDTO riderDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Rider partially : {}, {}", id, riderDTO);
        if (riderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, riderDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!riderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RiderDTO> result = riderService.partialUpdate(riderDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, riderDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /riders} : get all the riders.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of riders in body.
     */
    @GetMapping("/riders")
    public ResponseEntity<List<RiderDTO>> getAllRiders(
        RiderCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Riders by criteria: {}", criteria);
        Page<RiderDTO> page = riderQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /riders/count} : count all the riders.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/riders/count")
    public ResponseEntity<Long> countRiders(RiderCriteria criteria) {
        log.debug("REST request to count Riders by criteria: {}", criteria);
        return ResponseEntity.ok().body(riderQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /riders/:id} : get the "id" rider.
     *
     * @param id the id of the riderDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the riderDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/riders/{id}")
    public ResponseEntity<RiderDTO> getRider(@PathVariable Long id) {
        log.debug("REST request to get Rider : {}", id);
        Optional<RiderDTO> riderDTO = riderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(riderDTO);
    }

    /**
     * {@code DELETE  /riders/:id} : delete the "id" rider.
     *
     * @param id the id of the riderDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/riders/{id}")
    public ResponseEntity<Void> deleteRider(@PathVariable Long id) {
        log.debug("REST request to delete Rider : {}", id);
        riderService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
