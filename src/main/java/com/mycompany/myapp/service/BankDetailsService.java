package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.BankDetails;
import com.mycompany.myapp.repository.BankDetailsRepository;
import com.mycompany.myapp.service.dto.BankDetailsDTO;
import com.mycompany.myapp.service.mapper.BankDetailsMapper;
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
 * Service Implementation for managing {@link BankDetails}.
 */
@Service
@Transactional
public class BankDetailsService {

    private final Logger log = LoggerFactory.getLogger(BankDetailsService.class);

    private final BankDetailsRepository bankDetailsRepository;

    private final BankDetailsMapper bankDetailsMapper;

    public BankDetailsService(BankDetailsRepository bankDetailsRepository, BankDetailsMapper bankDetailsMapper) {
        this.bankDetailsRepository = bankDetailsRepository;
        this.bankDetailsMapper = bankDetailsMapper;
    }

    /**
     * Save a bankDetails.
     *
     * @param bankDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public BankDetailsDTO save(BankDetailsDTO bankDetailsDTO) {
        log.debug("Request to save BankDetails : {}", bankDetailsDTO);
        BankDetails bankDetails = bankDetailsMapper.toEntity(bankDetailsDTO);
        bankDetails = bankDetailsRepository.save(bankDetails);
        return bankDetailsMapper.toDto(bankDetails);
    }

    /**
     * Partially update a bankDetails.
     *
     * @param bankDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BankDetailsDTO> partialUpdate(BankDetailsDTO bankDetailsDTO) {
        log.debug("Request to partially update BankDetails : {}", bankDetailsDTO);

        return bankDetailsRepository
            .findById(bankDetailsDTO.getId())
            .map(existingBankDetails -> {
                bankDetailsMapper.partialUpdate(existingBankDetails, bankDetailsDTO);

                return existingBankDetails;
            })
            .map(bankDetailsRepository::save)
            .map(bankDetailsMapper::toDto);
    }

    /**
     * Get all the bankDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BankDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BankDetails");
        return bankDetailsRepository.findAll(pageable).map(bankDetailsMapper::toDto);
    }

    /**
     *  Get all the bankDetails where Policy is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BankDetailsDTO> findAllWherePolicyIsNull() {
        log.debug("Request to get all bankDetails where Policy is null");
        return StreamSupport
            .stream(bankDetailsRepository.findAll().spliterator(), false)
            .filter(bankDetails -> bankDetails.getPolicy() == null)
            .map(bankDetailsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one bankDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BankDetailsDTO> findOne(Long id) {
        log.debug("Request to get BankDetails : {}", id);
        return bankDetailsRepository.findById(id).map(bankDetailsMapper::toDto);
    }

    /**
     * Delete the bankDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BankDetails : {}", id);
        bankDetailsRepository.deleteById(id);
    }
}
