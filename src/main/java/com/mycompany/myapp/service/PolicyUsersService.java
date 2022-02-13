package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.PolicyUsers;
import com.mycompany.myapp.repository.PolicyUsersRepository;
import com.mycompany.myapp.service.dto.PolicyUsersDTO;
import com.mycompany.myapp.service.mapper.PolicyUsersMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PolicyUsers}.
 */
@Service
@Transactional
public class PolicyUsersService {

    private final Logger log = LoggerFactory.getLogger(PolicyUsersService.class);

    private final PolicyUsersRepository policyUsersRepository;

    private final PolicyUsersMapper policyUsersMapper;

    public PolicyUsersService(PolicyUsersRepository policyUsersRepository, PolicyUsersMapper policyUsersMapper) {
        this.policyUsersRepository = policyUsersRepository;
        this.policyUsersMapper = policyUsersMapper;
    }

    /**
     * Save a policyUsers.
     *
     * @param policyUsersDTO the entity to save.
     * @return the persisted entity.
     */
    public PolicyUsersDTO save(PolicyUsersDTO policyUsersDTO) {
        log.debug("Request to save PolicyUsers : {}", policyUsersDTO);
        PolicyUsers policyUsers = policyUsersMapper.toEntity(policyUsersDTO);
        policyUsers = policyUsersRepository.save(policyUsers);
        return policyUsersMapper.toDto(policyUsers);
    }

    /**
     * Partially update a policyUsers.
     *
     * @param policyUsersDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PolicyUsersDTO> partialUpdate(PolicyUsersDTO policyUsersDTO) {
        log.debug("Request to partially update PolicyUsers : {}", policyUsersDTO);

        return policyUsersRepository
            .findById(policyUsersDTO.getId())
            .map(existingPolicyUsers -> {
                policyUsersMapper.partialUpdate(existingPolicyUsers, policyUsersDTO);

                return existingPolicyUsers;
            })
            .map(policyUsersRepository::save)
            .map(policyUsersMapper::toDto);
    }

    /**
     * Get all the policyUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PolicyUsersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PolicyUsers");
        return policyUsersRepository.findAll(pageable).map(policyUsersMapper::toDto);
    }

    /**
     * Get one policyUsers by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PolicyUsersDTO> findOne(Long id) {
        log.debug("Request to get PolicyUsers : {}", id);
        return policyUsersRepository.findById(id).map(policyUsersMapper::toDto);
    }

    /**
     * Delete the policyUsers by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PolicyUsers : {}", id);
        policyUsersRepository.deleteById(id);
    }
}
