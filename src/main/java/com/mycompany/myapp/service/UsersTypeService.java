package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.UsersType;
import com.mycompany.myapp.repository.UsersTypeRepository;
import com.mycompany.myapp.service.dto.UsersTypeDTO;
import com.mycompany.myapp.service.mapper.UsersTypeMapper;
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
 * Service Implementation for managing {@link UsersType}.
 */
@Service
@Transactional
public class UsersTypeService {

    private final Logger log = LoggerFactory.getLogger(UsersTypeService.class);

    private final UsersTypeRepository usersTypeRepository;

    private final UsersTypeMapper usersTypeMapper;

    public UsersTypeService(UsersTypeRepository usersTypeRepository, UsersTypeMapper usersTypeMapper) {
        this.usersTypeRepository = usersTypeRepository;
        this.usersTypeMapper = usersTypeMapper;
    }

    /**
     * Save a usersType.
     *
     * @param usersTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public UsersTypeDTO save(UsersTypeDTO usersTypeDTO) {
        log.debug("Request to save UsersType : {}", usersTypeDTO);
        UsersType usersType = usersTypeMapper.toEntity(usersTypeDTO);
        usersType = usersTypeRepository.save(usersType);
        return usersTypeMapper.toDto(usersType);
    }

    /**
     * Partially update a usersType.
     *
     * @param usersTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UsersTypeDTO> partialUpdate(UsersTypeDTO usersTypeDTO) {
        log.debug("Request to partially update UsersType : {}", usersTypeDTO);

        return usersTypeRepository
            .findById(usersTypeDTO.getId())
            .map(existingUsersType -> {
                usersTypeMapper.partialUpdate(existingUsersType, usersTypeDTO);

                return existingUsersType;
            })
            .map(usersTypeRepository::save)
            .map(usersTypeMapper::toDto);
    }

    /**
     * Get all the usersTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UsersTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UsersTypes");
        return usersTypeRepository.findAll(pageable).map(usersTypeMapper::toDto);
    }

    /**
     *  Get all the usersTypes where PolicyUsers is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UsersTypeDTO> findAllWherePolicyUsersIsNull() {
        log.debug("Request to get all usersTypes where PolicyUsers is null");
        return StreamSupport
            .stream(usersTypeRepository.findAll().spliterator(), false)
            .filter(usersType -> usersType.getPolicyUsers() == null)
            .map(usersTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one usersType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UsersTypeDTO> findOne(Long id) {
        log.debug("Request to get UsersType : {}", id);
        return usersTypeRepository.findById(id).map(usersTypeMapper::toDto);
    }

    /**
     * Delete the usersType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UsersType : {}", id);
        usersTypeRepository.deleteById(id);
    }
}
