package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.ProductDetailsRepository;
import com.mycompany.myapp.service.ProductDetailsQueryService;
import com.mycompany.myapp.service.ProductDetailsService;
import com.mycompany.myapp.service.criteria.ProductDetailsCriteria;
import com.mycompany.myapp.service.dto.ProductDetailsDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ProductDetails}.
 */
@RestController
@RequestMapping("/api")
public class ProductDetailsResource {

    private final Logger log = LoggerFactory.getLogger(ProductDetailsResource.class);

    private static final String ENTITY_NAME = "productDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductDetailsService productDetailsService;

    private final ProductDetailsRepository productDetailsRepository;

    private final ProductDetailsQueryService productDetailsQueryService;

    public ProductDetailsResource(
        ProductDetailsService productDetailsService,
        ProductDetailsRepository productDetailsRepository,
        ProductDetailsQueryService productDetailsQueryService
    ) {
        this.productDetailsService = productDetailsService;
        this.productDetailsRepository = productDetailsRepository;
        this.productDetailsQueryService = productDetailsQueryService;
    }

    /**
     * {@code POST  /product-details} : Create a new productDetails.
     *
     * @param productDetailsDTO the productDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productDetailsDTO, or with status {@code 400 (Bad Request)} if the productDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-details")
    public ResponseEntity<ProductDetailsDTO> createProductDetails(@Valid @RequestBody ProductDetailsDTO productDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProductDetails : {}", productDetailsDTO);
        if (productDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new productDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductDetailsDTO result = productDetailsService.save(productDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/product-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-details/:id} : Updates an existing productDetails.
     *
     * @param id the id of the productDetailsDTO to save.
     * @param productDetailsDTO the productDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the productDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-details/{id}")
    public ResponseEntity<ProductDetailsDTO> updateProductDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductDetailsDTO productDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductDetails : {}, {}", id, productDetailsDTO);
        if (productDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductDetailsDTO result = productDetailsService.save(productDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-details/:id} : Partial updates given fields of an existing productDetails, field will ignore if it is null
     *
     * @param id the id of the productDetailsDTO to save.
     * @param productDetailsDTO the productDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the productDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProductDetailsDTO> partialUpdateProductDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductDetailsDTO productDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductDetails partially : {}, {}", id, productDetailsDTO);
        if (productDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductDetailsDTO> result = productDetailsService.partialUpdate(productDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-details} : get all the productDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productDetails in body.
     */
    @GetMapping("/product-details")
    public ResponseEntity<List<ProductDetailsDTO>> getAllProductDetails(
        ProductDetailsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ProductDetails by criteria: {}", criteria);
        Page<ProductDetailsDTO> page = productDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-details/count} : count all the productDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/product-details/count")
    public ResponseEntity<Long> countProductDetails(ProductDetailsCriteria criteria) {
        log.debug("REST request to count ProductDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(productDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-details/:id} : get the "id" productDetails.
     *
     * @param id the id of the productDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-details/{id}")
    public ResponseEntity<ProductDetailsDTO> getProductDetails(@PathVariable Long id) {
        log.debug("REST request to get ProductDetails : {}", id);
        Optional<ProductDetailsDTO> productDetailsDTO = productDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productDetailsDTO);
    }

    /**
     * {@code DELETE  /product-details/:id} : delete the "id" productDetails.
     *
     * @param id the id of the productDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-details/{id}")
    public ResponseEntity<Void> deleteProductDetails(@PathVariable Long id) {
        log.debug("REST request to delete ProductDetails : {}", id);
        productDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
