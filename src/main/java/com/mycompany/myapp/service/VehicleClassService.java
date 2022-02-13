package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.VehicleClass;
import com.mycompany.myapp.repository.VehicleClassRepository;
import com.mycompany.myapp.service.dto.VehicleClassDTO;
import com.mycompany.myapp.service.mapper.VehicleClassMapper;
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
 * Service Implementation for managing {@link VehicleClass}.
 */
@Service
@Transactional
public class VehicleClassService {

    private final Logger log = LoggerFactory.getLogger(VehicleClassService.class);

    private final VehicleClassRepository vehicleClassRepository;

    private final VehicleClassMapper vehicleClassMapper;

    public VehicleClassService(VehicleClassRepository vehicleClassRepository, VehicleClassMapper vehicleClassMapper) {
        this.vehicleClassRepository = vehicleClassRepository;
        this.vehicleClassMapper = vehicleClassMapper;
    }

    /**
     * Save a vehicleClass.
     *
     * @param vehicleClassDTO the entity to save.
     * @return the persisted entity.
     */
    public VehicleClassDTO save(VehicleClassDTO vehicleClassDTO) {
        log.debug("Request to save VehicleClass : {}", vehicleClassDTO);
        VehicleClass vehicleClass = vehicleClassMapper.toEntity(vehicleClassDTO);
        vehicleClass = vehicleClassRepository.save(vehicleClass);
        return vehicleClassMapper.toDto(vehicleClass);
    }

    /**
     * Partially update a vehicleClass.
     *
     * @param vehicleClassDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<VehicleClassDTO> partialUpdate(VehicleClassDTO vehicleClassDTO) {
        log.debug("Request to partially update VehicleClass : {}", vehicleClassDTO);

        return vehicleClassRepository
            .findById(vehicleClassDTO.getId())
            .map(existingVehicleClass -> {
                vehicleClassMapper.partialUpdate(existingVehicleClass, vehicleClassDTO);

                return existingVehicleClass;
            })
            .map(vehicleClassRepository::save)
            .map(vehicleClassMapper::toDto);
    }

    /**
     * Get all the vehicleClasses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VehicleClassDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VehicleClasses");
        return vehicleClassRepository.findAll(pageable).map(vehicleClassMapper::toDto);
    }

    /**
     *  Get all the vehicleClasses where Policy is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<VehicleClassDTO> findAllWherePolicyIsNull() {
        log.debug("Request to get all vehicleClasses where Policy is null");
        return StreamSupport
            .stream(vehicleClassRepository.findAll().spliterator(), false)
            .filter(vehicleClass -> vehicleClass.getPolicy() == null)
            .map(vehicleClassMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one vehicleClass by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VehicleClassDTO> findOne(Long id) {
        log.debug("Request to get VehicleClass : {}", id);
        return vehicleClassRepository.findById(id).map(vehicleClassMapper::toDto);
    }

    /**
     * Delete the vehicleClass by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete VehicleClass : {}", id);
        vehicleClassRepository.deleteById(id);
    }
}
