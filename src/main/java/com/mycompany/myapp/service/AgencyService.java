package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Agency;
import com.mycompany.myapp.repository.AgencyRepository;
import com.mycompany.myapp.service.dto.AgencyDTO;
import com.mycompany.myapp.service.mapper.AgencyMapper;
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
 * Service Implementation for managing {@link Agency}.
 */
@Service
@Transactional
public class AgencyService {

    private final Logger log = LoggerFactory.getLogger(AgencyService.class);

    private final AgencyRepository agencyRepository;

    private final AgencyMapper agencyMapper;

    public AgencyService(AgencyRepository agencyRepository, AgencyMapper agencyMapper) {
        this.agencyRepository = agencyRepository;
        this.agencyMapper = agencyMapper;
    }

    /**
     * Save a agency.
     *
     * @param agencyDTO the entity to save.
     * @return the persisted entity.
     */
    public AgencyDTO save(AgencyDTO agencyDTO) {
        log.debug("Request to save Agency : {}", agencyDTO);
        Agency agency = agencyMapper.toEntity(agencyDTO);
        agency = agencyRepository.save(agency);
        return agencyMapper.toDto(agency);
    }

    /**
     * Partially update a agency.
     *
     * @param agencyDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AgencyDTO> partialUpdate(AgencyDTO agencyDTO) {
        log.debug("Request to partially update Agency : {}", agencyDTO);

        return agencyRepository
            .findById(agencyDTO.getId())
            .map(existingAgency -> {
                agencyMapper.partialUpdate(existingAgency, agencyDTO);

                return existingAgency;
            })
            .map(agencyRepository::save)
            .map(agencyMapper::toDto);
    }

    /**
     * Get all the agencies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AgencyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Agencies");
        return agencyRepository.findAll(pageable).map(agencyMapper::toDto);
    }

    /**
     *  Get all the agencies where Policy is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AgencyDTO> findAllWherePolicyIsNull() {
        log.debug("Request to get all agencies where Policy is null");
        return StreamSupport
            .stream(agencyRepository.findAll().spliterator(), false)
            .filter(agency -> agency.getPolicy() == null)
            .map(agencyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one agency by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AgencyDTO> findOne(Long id) {
        log.debug("Request to get Agency : {}", id);
        return agencyRepository.findById(id).map(agencyMapper::toDto);
    }

    /**
     * Delete the agency by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Agency : {}", id);
        agencyRepository.deleteById(id);
    }
}
