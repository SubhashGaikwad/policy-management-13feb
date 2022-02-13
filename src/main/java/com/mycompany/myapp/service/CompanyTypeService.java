package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.CompanyType;
import com.mycompany.myapp.repository.CompanyTypeRepository;
import com.mycompany.myapp.service.dto.CompanyTypeDTO;
import com.mycompany.myapp.service.mapper.CompanyTypeMapper;
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
 * Service Implementation for managing {@link CompanyType}.
 */
@Service
@Transactional
public class CompanyTypeService {

    private final Logger log = LoggerFactory.getLogger(CompanyTypeService.class);

    private final CompanyTypeRepository companyTypeRepository;

    private final CompanyTypeMapper companyTypeMapper;

    public CompanyTypeService(CompanyTypeRepository companyTypeRepository, CompanyTypeMapper companyTypeMapper) {
        this.companyTypeRepository = companyTypeRepository;
        this.companyTypeMapper = companyTypeMapper;
    }

    /**
     * Save a companyType.
     *
     * @param companyTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public CompanyTypeDTO save(CompanyTypeDTO companyTypeDTO) {
        log.debug("Request to save CompanyType : {}", companyTypeDTO);
        CompanyType companyType = companyTypeMapper.toEntity(companyTypeDTO);
        companyType = companyTypeRepository.save(companyType);
        return companyTypeMapper.toDto(companyType);
    }

    /**
     * Partially update a companyType.
     *
     * @param companyTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CompanyTypeDTO> partialUpdate(CompanyTypeDTO companyTypeDTO) {
        log.debug("Request to partially update CompanyType : {}", companyTypeDTO);

        return companyTypeRepository
            .findById(companyTypeDTO.getId())
            .map(existingCompanyType -> {
                companyTypeMapper.partialUpdate(existingCompanyType, companyTypeDTO);

                return existingCompanyType;
            })
            .map(companyTypeRepository::save)
            .map(companyTypeMapper::toDto);
    }

    /**
     * Get all the companyTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CompanyTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CompanyTypes");
        return companyTypeRepository.findAll(pageable).map(companyTypeMapper::toDto);
    }

    /**
     *  Get all the companyTypes where Company is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CompanyTypeDTO> findAllWhereCompanyIsNull() {
        log.debug("Request to get all companyTypes where Company is null");
        return StreamSupport
            .stream(companyTypeRepository.findAll().spliterator(), false)
            .filter(companyType -> companyType.getCompany() == null)
            .map(companyTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one companyType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CompanyTypeDTO> findOne(Long id) {
        log.debug("Request to get CompanyType : {}", id);
        return companyTypeRepository.findById(id).map(companyTypeMapper::toDto);
    }

    /**
     * Delete the companyType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CompanyType : {}", id);
        companyTypeRepository.deleteById(id);
    }
}
