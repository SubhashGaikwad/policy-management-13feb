package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Nominee;
import com.mycompany.myapp.repository.NomineeRepository;
import com.mycompany.myapp.service.dto.NomineeDTO;
import com.mycompany.myapp.service.mapper.NomineeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Nominee}.
 */
@Service
@Transactional
public class NomineeService {

    private final Logger log = LoggerFactory.getLogger(NomineeService.class);

    private final NomineeRepository nomineeRepository;

    private final NomineeMapper nomineeMapper;

    public NomineeService(NomineeRepository nomineeRepository, NomineeMapper nomineeMapper) {
        this.nomineeRepository = nomineeRepository;
        this.nomineeMapper = nomineeMapper;
    }

    /**
     * Save a nominee.
     *
     * @param nomineeDTO the entity to save.
     * @return the persisted entity.
     */
    public NomineeDTO save(NomineeDTO nomineeDTO) {
        log.debug("Request to save Nominee : {}", nomineeDTO);
        Nominee nominee = nomineeMapper.toEntity(nomineeDTO);
        nominee = nomineeRepository.save(nominee);
        return nomineeMapper.toDto(nominee);
    }

    /**
     * Partially update a nominee.
     *
     * @param nomineeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NomineeDTO> partialUpdate(NomineeDTO nomineeDTO) {
        log.debug("Request to partially update Nominee : {}", nomineeDTO);

        return nomineeRepository
            .findById(nomineeDTO.getId())
            .map(existingNominee -> {
                nomineeMapper.partialUpdate(existingNominee, nomineeDTO);

                return existingNominee;
            })
            .map(nomineeRepository::save)
            .map(nomineeMapper::toDto);
    }

    /**
     * Get all the nominees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NomineeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Nominees");
        return nomineeRepository.findAll(pageable).map(nomineeMapper::toDto);
    }

    /**
     * Get one nominee by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NomineeDTO> findOne(Long id) {
        log.debug("Request to get Nominee : {}", id);
        return nomineeRepository.findById(id).map(nomineeMapper::toDto);
    }

    /**
     * Delete the nominee by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Nominee : {}", id);
        nomineeRepository.deleteById(id);
    }
}
