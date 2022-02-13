package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.BankDetailsRepository;
import com.mycompany.myapp.service.BankDetailsQueryService;
import com.mycompany.myapp.service.BankDetailsService;
import com.mycompany.myapp.service.criteria.BankDetailsCriteria;
import com.mycompany.myapp.service.dto.BankDetailsDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.BankDetails}.
 */
@RestController
@RequestMapping("/api")
public class BankDetailsResource {

    private final Logger log = LoggerFactory.getLogger(BankDetailsResource.class);

    private static final String ENTITY_NAME = "bankDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankDetailsService bankDetailsService;

    private final BankDetailsRepository bankDetailsRepository;

    private final BankDetailsQueryService bankDetailsQueryService;

    public BankDetailsResource(
        BankDetailsService bankDetailsService,
        BankDetailsRepository bankDetailsRepository,
        BankDetailsQueryService bankDetailsQueryService
    ) {
        this.bankDetailsService = bankDetailsService;
        this.bankDetailsRepository = bankDetailsRepository;
        this.bankDetailsQueryService = bankDetailsQueryService;
    }

    /**
     * {@code POST  /bank-details} : Create a new bankDetails.
     *
     * @param bankDetailsDTO the bankDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankDetailsDTO, or with status {@code 400 (Bad Request)} if the bankDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bank-details")
    public ResponseEntity<BankDetailsDTO> createBankDetails(@Valid @RequestBody BankDetailsDTO bankDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save BankDetails : {}", bankDetailsDTO);
        if (bankDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new bankDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankDetailsDTO result = bankDetailsService.save(bankDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/bank-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bank-details/:id} : Updates an existing bankDetails.
     *
     * @param id the id of the bankDetailsDTO to save.
     * @param bankDetailsDTO the bankDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the bankDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bank-details/{id}")
    public ResponseEntity<BankDetailsDTO> updateBankDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BankDetailsDTO bankDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BankDetails : {}, {}", id, bankDetailsDTO);
        if (bankDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BankDetailsDTO result = bankDetailsService.save(bankDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bank-details/:id} : Partial updates given fields of an existing bankDetails, field will ignore if it is null
     *
     * @param id the id of the bankDetailsDTO to save.
     * @param bankDetailsDTO the bankDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the bankDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bankDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bankDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bank-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BankDetailsDTO> partialUpdateBankDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BankDetailsDTO bankDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BankDetails partially : {}, {}", id, bankDetailsDTO);
        if (bankDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BankDetailsDTO> result = bankDetailsService.partialUpdate(bankDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bank-details} : get all the bankDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankDetails in body.
     */
    @GetMapping("/bank-details")
    public ResponseEntity<List<BankDetailsDTO>> getAllBankDetails(
        BankDetailsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get BankDetails by criteria: {}", criteria);
        Page<BankDetailsDTO> page = bankDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bank-details/count} : count all the bankDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/bank-details/count")
    public ResponseEntity<Long> countBankDetails(BankDetailsCriteria criteria) {
        log.debug("REST request to count BankDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(bankDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /bank-details/:id} : get the "id" bankDetails.
     *
     * @param id the id of the bankDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bank-details/{id}")
    public ResponseEntity<BankDetailsDTO> getBankDetails(@PathVariable Long id) {
        log.debug("REST request to get BankDetails : {}", id);
        Optional<BankDetailsDTO> bankDetailsDTO = bankDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bankDetailsDTO);
    }

    /**
     * {@code DELETE  /bank-details/:id} : delete the "id" bankDetails.
     *
     * @param id the id of the bankDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bank-details/{id}")
    public ResponseEntity<Void> deleteBankDetails(@PathVariable Long id) {
        log.debug("REST request to delete BankDetails : {}", id);
        bankDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
