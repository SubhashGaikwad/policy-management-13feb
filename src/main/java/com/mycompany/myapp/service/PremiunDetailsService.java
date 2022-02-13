package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.PremiunDetails;
import com.mycompany.myapp.repository.PremiunDetailsRepository;
import com.mycompany.myapp.service.dto.PremiunDetailsDTO;
import com.mycompany.myapp.service.mapper.PremiunDetailsMapper;
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
 * Service Implementation for managing {@link PremiunDetails}.
 */
@Service
@Transactional
public class PremiunDetailsService {

    private final Logger log = LoggerFactory.getLogger(PremiunDetailsService.class);

    private final PremiunDetailsRepository premiunDetailsRepository;

    private final PremiunDetailsMapper premiunDetailsMapper;

    public PremiunDetailsService(PremiunDetailsRepository premiunDetailsRepository, PremiunDetailsMapper premiunDetailsMapper) {
        this.premiunDetailsRepository = premiunDetailsRepository;
        this.premiunDetailsMapper = premiunDetailsMapper;
    }

    /**
     * Save a premiunDetails.
     *
     * @param premiunDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public PremiunDetailsDTO save(PremiunDetailsDTO premiunDetailsDTO) {
        log.debug("Request to save PremiunDetails : {}", premiunDetailsDTO);
        PremiunDetails premiunDetails = premiunDetailsMapper.toEntity(premiunDetailsDTO);
        premiunDetails = premiunDetailsRepository.save(premiunDetails);
        return premiunDetailsMapper.toDto(premiunDetails);
    }

    /**
     * Partially update a premiunDetails.
     *
     * @param premiunDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PremiunDetailsDTO> partialUpdate(PremiunDetailsDTO premiunDetailsDTO) {
        log.debug("Request to partially update PremiunDetails : {}", premiunDetailsDTO);

        return premiunDetailsRepository
            .findById(premiunDetailsDTO.getId())
            .map(existingPremiunDetails -> {
                premiunDetailsMapper.partialUpdate(existingPremiunDetails, premiunDetailsDTO);

                return existingPremiunDetails;
            })
            .map(premiunDetailsRepository::save)
            .map(premiunDetailsMapper::toDto);
    }

    /**
     * Get all the premiunDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PremiunDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PremiunDetails");
        return premiunDetailsRepository.findAll(pageable).map(premiunDetailsMapper::toDto);
    }

    /**
     *  Get all the premiunDetails where Policy is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PremiunDetailsDTO> findAllWherePolicyIsNull() {
        log.debug("Request to get all premiunDetails where Policy is null");
        return StreamSupport
            .stream(premiunDetailsRepository.findAll().spliterator(), false)
            .filter(premiunDetails -> premiunDetails.getPolicy() == null)
            .map(premiunDetailsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one premiunDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PremiunDetailsDTO> findOne(Long id) {
        log.debug("Request to get PremiunDetails : {}", id);
        return premiunDetailsRepository.findById(id).map(premiunDetailsMapper::toDto);
    }

    /**
     * Delete the premiunDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PremiunDetails : {}", id);
        premiunDetailsRepository.deleteById(id);
    }
}
