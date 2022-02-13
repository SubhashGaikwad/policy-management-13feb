package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.PremiunDetailsRepository;
import com.mycompany.myapp.service.PremiunDetailsQueryService;
import com.mycompany.myapp.service.PremiunDetailsService;
import com.mycompany.myapp.service.criteria.PremiunDetailsCriteria;
import com.mycompany.myapp.service.dto.PremiunDetailsDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.PremiunDetails}.
 */
@RestController
@RequestMapping("/api")
public class PremiunDetailsResource {

    private final Logger log = LoggerFactory.getLogger(PremiunDetailsResource.class);

    private static final String ENTITY_NAME = "premiunDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PremiunDetailsService premiunDetailsService;

    private final PremiunDetailsRepository premiunDetailsRepository;

    private final PremiunDetailsQueryService premiunDetailsQueryService;

    public PremiunDetailsResource(
        PremiunDetailsService premiunDetailsService,
        PremiunDetailsRepository premiunDetailsRepository,
        PremiunDetailsQueryService premiunDetailsQueryService
    ) {
        this.premiunDetailsService = premiunDetailsService;
        this.premiunDetailsRepository = premiunDetailsRepository;
        this.premiunDetailsQueryService = premiunDetailsQueryService;
    }

    /**
     * {@code POST  /premiun-details} : Create a new premiunDetails.
     *
     * @param premiunDetailsDTO the premiunDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new premiunDetailsDTO, or with status {@code 400 (Bad Request)} if the premiunDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/premiun-details")
    public ResponseEntity<PremiunDetailsDTO> createPremiunDetails(@Valid @RequestBody PremiunDetailsDTO premiunDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to save PremiunDetails : {}", premiunDetailsDTO);
        if (premiunDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new premiunDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PremiunDetailsDTO result = premiunDetailsService.save(premiunDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/premiun-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /premiun-details/:id} : Updates an existing premiunDetails.
     *
     * @param id the id of the premiunDetailsDTO to save.
     * @param premiunDetailsDTO the premiunDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated premiunDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the premiunDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the premiunDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/premiun-details/{id}")
    public ResponseEntity<PremiunDetailsDTO> updatePremiunDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PremiunDetailsDTO premiunDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PremiunDetails : {}, {}", id, premiunDetailsDTO);
        if (premiunDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, premiunDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!premiunDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PremiunDetailsDTO result = premiunDetailsService.save(premiunDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, premiunDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /premiun-details/:id} : Partial updates given fields of an existing premiunDetails, field will ignore if it is null
     *
     * @param id the id of the premiunDetailsDTO to save.
     * @param premiunDetailsDTO the premiunDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated premiunDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the premiunDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the premiunDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the premiunDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/premiun-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PremiunDetailsDTO> partialUpdatePremiunDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PremiunDetailsDTO premiunDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PremiunDetails partially : {}, {}", id, premiunDetailsDTO);
        if (premiunDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, premiunDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!premiunDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PremiunDetailsDTO> result = premiunDetailsService.partialUpdate(premiunDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, premiunDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /premiun-details} : get all the premiunDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of premiunDetails in body.
     */
    @GetMapping("/premiun-details")
    public ResponseEntity<List<PremiunDetailsDTO>> getAllPremiunDetails(
        PremiunDetailsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PremiunDetails by criteria: {}", criteria);
        Page<PremiunDetailsDTO> page = premiunDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /premiun-details/count} : count all the premiunDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/premiun-details/count")
    public ResponseEntity<Long> countPremiunDetails(PremiunDetailsCriteria criteria) {
        log.debug("REST request to count PremiunDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(premiunDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /premiun-details/:id} : get the "id" premiunDetails.
     *
     * @param id the id of the premiunDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the premiunDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/premiun-details/{id}")
    public ResponseEntity<PremiunDetailsDTO> getPremiunDetails(@PathVariable Long id) {
        log.debug("REST request to get PremiunDetails : {}", id);
        Optional<PremiunDetailsDTO> premiunDetailsDTO = premiunDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(premiunDetailsDTO);
    }

    /**
     * {@code DELETE  /premiun-details/:id} : delete the "id" premiunDetails.
     *
     * @param id the id of the premiunDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/premiun-details/{id}")
    public ResponseEntity<Void> deletePremiunDetails(@PathVariable Long id) {
        log.debug("REST request to delete PremiunDetails : {}", id);
        premiunDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
