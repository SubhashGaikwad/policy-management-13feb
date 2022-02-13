package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.VehicleDetailsRepository;
import com.mycompany.myapp.service.VehicleDetailsQueryService;
import com.mycompany.myapp.service.VehicleDetailsService;
import com.mycompany.myapp.service.criteria.VehicleDetailsCriteria;
import com.mycompany.myapp.service.dto.VehicleDetailsDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.VehicleDetails}.
 */
@RestController
@RequestMapping("/api")
public class VehicleDetailsResource {

    private final Logger log = LoggerFactory.getLogger(VehicleDetailsResource.class);

    private static final String ENTITY_NAME = "vehicleDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VehicleDetailsService vehicleDetailsService;

    private final VehicleDetailsRepository vehicleDetailsRepository;

    private final VehicleDetailsQueryService vehicleDetailsQueryService;

    public VehicleDetailsResource(
        VehicleDetailsService vehicleDetailsService,
        VehicleDetailsRepository vehicleDetailsRepository,
        VehicleDetailsQueryService vehicleDetailsQueryService
    ) {
        this.vehicleDetailsService = vehicleDetailsService;
        this.vehicleDetailsRepository = vehicleDetailsRepository;
        this.vehicleDetailsQueryService = vehicleDetailsQueryService;
    }

    /**
     * {@code POST  /vehicle-details} : Create a new vehicleDetails.
     *
     * @param vehicleDetailsDTO the vehicleDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vehicleDetailsDTO, or with status {@code 400 (Bad Request)} if the vehicleDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vehicle-details")
    public ResponseEntity<VehicleDetailsDTO> createVehicleDetails(@Valid @RequestBody VehicleDetailsDTO vehicleDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to save VehicleDetails : {}", vehicleDetailsDTO);
        if (vehicleDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new vehicleDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VehicleDetailsDTO result = vehicleDetailsService.save(vehicleDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/vehicle-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vehicle-details/:id} : Updates an existing vehicleDetails.
     *
     * @param id the id of the vehicleDetailsDTO to save.
     * @param vehicleDetailsDTO the vehicleDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vehicleDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the vehicleDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vehicleDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vehicle-details/{id}")
    public ResponseEntity<VehicleDetailsDTO> updateVehicleDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VehicleDetailsDTO vehicleDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update VehicleDetails : {}, {}", id, vehicleDetailsDTO);
        if (vehicleDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vehicleDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vehicleDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VehicleDetailsDTO result = vehicleDetailsService.save(vehicleDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vehicleDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /vehicle-details/:id} : Partial updates given fields of an existing vehicleDetails, field will ignore if it is null
     *
     * @param id the id of the vehicleDetailsDTO to save.
     * @param vehicleDetailsDTO the vehicleDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vehicleDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the vehicleDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the vehicleDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the vehicleDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/vehicle-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VehicleDetailsDTO> partialUpdateVehicleDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VehicleDetailsDTO vehicleDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update VehicleDetails partially : {}, {}", id, vehicleDetailsDTO);
        if (vehicleDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vehicleDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vehicleDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VehicleDetailsDTO> result = vehicleDetailsService.partialUpdate(vehicleDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vehicleDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /vehicle-details} : get all the vehicleDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vehicleDetails in body.
     */
    @GetMapping("/vehicle-details")
    public ResponseEntity<List<VehicleDetailsDTO>> getAllVehicleDetails(
        VehicleDetailsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get VehicleDetails by criteria: {}", criteria);
        Page<VehicleDetailsDTO> page = vehicleDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vehicle-details/count} : count all the vehicleDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/vehicle-details/count")
    public ResponseEntity<Long> countVehicleDetails(VehicleDetailsCriteria criteria) {
        log.debug("REST request to count VehicleDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(vehicleDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /vehicle-details/:id} : get the "id" vehicleDetails.
     *
     * @param id the id of the vehicleDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vehicleDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vehicle-details/{id}")
    public ResponseEntity<VehicleDetailsDTO> getVehicleDetails(@PathVariable Long id) {
        log.debug("REST request to get VehicleDetails : {}", id);
        Optional<VehicleDetailsDTO> vehicleDetailsDTO = vehicleDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vehicleDetailsDTO);
    }

    /**
     * {@code DELETE  /vehicle-details/:id} : delete the "id" vehicleDetails.
     *
     * @param id the id of the vehicleDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vehicle-details/{id}")
    public ResponseEntity<Void> deleteVehicleDetails(@PathVariable Long id) {
        log.debug("REST request to delete VehicleDetails : {}", id);
        vehicleDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
