package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.VehicleClassRepository;
import com.mycompany.myapp.service.VehicleClassQueryService;
import com.mycompany.myapp.service.VehicleClassService;
import com.mycompany.myapp.service.criteria.VehicleClassCriteria;
import com.mycompany.myapp.service.dto.VehicleClassDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.VehicleClass}.
 */
@RestController
@RequestMapping("/api")
public class VehicleClassResource {

    private final Logger log = LoggerFactory.getLogger(VehicleClassResource.class);

    private static final String ENTITY_NAME = "vehicleClass";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VehicleClassService vehicleClassService;

    private final VehicleClassRepository vehicleClassRepository;

    private final VehicleClassQueryService vehicleClassQueryService;

    public VehicleClassResource(
        VehicleClassService vehicleClassService,
        VehicleClassRepository vehicleClassRepository,
        VehicleClassQueryService vehicleClassQueryService
    ) {
        this.vehicleClassService = vehicleClassService;
        this.vehicleClassRepository = vehicleClassRepository;
        this.vehicleClassQueryService = vehicleClassQueryService;
    }

    /**
     * {@code POST  /vehicle-classes} : Create a new vehicleClass.
     *
     * @param vehicleClassDTO the vehicleClassDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vehicleClassDTO, or with status {@code 400 (Bad Request)} if the vehicleClass has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vehicle-classes")
    public ResponseEntity<VehicleClassDTO> createVehicleClass(@Valid @RequestBody VehicleClassDTO vehicleClassDTO)
        throws URISyntaxException {
        log.debug("REST request to save VehicleClass : {}", vehicleClassDTO);
        if (vehicleClassDTO.getId() != null) {
            throw new BadRequestAlertException("A new vehicleClass cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VehicleClassDTO result = vehicleClassService.save(vehicleClassDTO);
        return ResponseEntity
            .created(new URI("/api/vehicle-classes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vehicle-classes/:id} : Updates an existing vehicleClass.
     *
     * @param id the id of the vehicleClassDTO to save.
     * @param vehicleClassDTO the vehicleClassDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vehicleClassDTO,
     * or with status {@code 400 (Bad Request)} if the vehicleClassDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vehicleClassDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vehicle-classes/{id}")
    public ResponseEntity<VehicleClassDTO> updateVehicleClass(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VehicleClassDTO vehicleClassDTO
    ) throws URISyntaxException {
        log.debug("REST request to update VehicleClass : {}, {}", id, vehicleClassDTO);
        if (vehicleClassDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vehicleClassDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vehicleClassRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VehicleClassDTO result = vehicleClassService.save(vehicleClassDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vehicleClassDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /vehicle-classes/:id} : Partial updates given fields of an existing vehicleClass, field will ignore if it is null
     *
     * @param id the id of the vehicleClassDTO to save.
     * @param vehicleClassDTO the vehicleClassDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vehicleClassDTO,
     * or with status {@code 400 (Bad Request)} if the vehicleClassDTO is not valid,
     * or with status {@code 404 (Not Found)} if the vehicleClassDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the vehicleClassDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/vehicle-classes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VehicleClassDTO> partialUpdateVehicleClass(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VehicleClassDTO vehicleClassDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update VehicleClass partially : {}, {}", id, vehicleClassDTO);
        if (vehicleClassDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vehicleClassDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vehicleClassRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VehicleClassDTO> result = vehicleClassService.partialUpdate(vehicleClassDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vehicleClassDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /vehicle-classes} : get all the vehicleClasses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vehicleClasses in body.
     */
    @GetMapping("/vehicle-classes")
    public ResponseEntity<List<VehicleClassDTO>> getAllVehicleClasses(
        VehicleClassCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get VehicleClasses by criteria: {}", criteria);
        Page<VehicleClassDTO> page = vehicleClassQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vehicle-classes/count} : count all the vehicleClasses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/vehicle-classes/count")
    public ResponseEntity<Long> countVehicleClasses(VehicleClassCriteria criteria) {
        log.debug("REST request to count VehicleClasses by criteria: {}", criteria);
        return ResponseEntity.ok().body(vehicleClassQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /vehicle-classes/:id} : get the "id" vehicleClass.
     *
     * @param id the id of the vehicleClassDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vehicleClassDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vehicle-classes/{id}")
    public ResponseEntity<VehicleClassDTO> getVehicleClass(@PathVariable Long id) {
        log.debug("REST request to get VehicleClass : {}", id);
        Optional<VehicleClassDTO> vehicleClassDTO = vehicleClassService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vehicleClassDTO);
    }

    /**
     * {@code DELETE  /vehicle-classes/:id} : delete the "id" vehicleClass.
     *
     * @param id the id of the vehicleClassDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vehicle-classes/{id}")
    public ResponseEntity<Void> deleteVehicleClass(@PathVariable Long id) {
        log.debug("REST request to delete VehicleClass : {}", id);
        vehicleClassService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
