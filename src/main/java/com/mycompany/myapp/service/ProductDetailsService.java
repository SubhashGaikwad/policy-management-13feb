package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.ProductDetails;
import com.mycompany.myapp.repository.ProductDetailsRepository;
import com.mycompany.myapp.service.dto.ProductDetailsDTO;
import com.mycompany.myapp.service.mapper.ProductDetailsMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductDetails}.
 */
@Service
@Transactional
public class ProductDetailsService {

    private final Logger log = LoggerFactory.getLogger(ProductDetailsService.class);

    private final ProductDetailsRepository productDetailsRepository;

    private final ProductDetailsMapper productDetailsMapper;

    public ProductDetailsService(ProductDetailsRepository productDetailsRepository, ProductDetailsMapper productDetailsMapper) {
        this.productDetailsRepository = productDetailsRepository;
        this.productDetailsMapper = productDetailsMapper;
    }

    /**
     * Save a productDetails.
     *
     * @param productDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductDetailsDTO save(ProductDetailsDTO productDetailsDTO) {
        log.debug("Request to save ProductDetails : {}", productDetailsDTO);
        ProductDetails productDetails = productDetailsMapper.toEntity(productDetailsDTO);
        productDetails = productDetailsRepository.save(productDetails);
        return productDetailsMapper.toDto(productDetails);
    }

    /**
     * Partially update a productDetails.
     *
     * @param productDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProductDetailsDTO> partialUpdate(ProductDetailsDTO productDetailsDTO) {
        log.debug("Request to partially update ProductDetails : {}", productDetailsDTO);

        return productDetailsRepository
            .findById(productDetailsDTO.getId())
            .map(existingProductDetails -> {
                productDetailsMapper.partialUpdate(existingProductDetails, productDetailsDTO);

                return existingProductDetails;
            })
            .map(productDetailsRepository::save)
            .map(productDetailsMapper::toDto);
    }

    /**
     * Get all the productDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductDetails");
        return productDetailsRepository.findAll(pageable).map(productDetailsMapper::toDto);
    }

    /**
     *  Get all the productDetails where Product is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProductDetailsDTO> findAllWhereProductIsNull() {
        log.debug("Request to get all productDetails where Product is null");
        return StreamSupport
            .stream(productDetailsRepository.findAll().spliterator(), false)
            .filter(productDetails -> productDetails.getProduct() == null)
            .map(productDetailsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one productDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductDetailsDTO> findOne(Long id) {
        log.debug("Request to get ProductDetails : {}", id);
        return productDetailsRepository.findById(id).map(productDetailsMapper::toDto);
    }

    /**
     * Delete the productDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductDetails : {}", id);
        productDetailsRepository.deleteById(id);
    }
}
