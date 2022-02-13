package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.VehicleDetails;
import com.mycompany.myapp.repository.VehicleDetailsRepository;
import com.mycompany.myapp.service.dto.VehicleDetailsDTO;
import com.mycompany.myapp.service.mapper.VehicleDetailsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link VehicleDetails}.
 */
@Service
@Transactional
public class VehicleDetailsService {

    private final Logger log = LoggerFactory.getLogger(VehicleDetailsService.class);

    private final VehicleDetailsRepository vehicleDetailsRepository;

    private final VehicleDetailsMapper vehicleDetailsMapper;

    public VehicleDetailsService(VehicleDetailsRepository vehicleDetailsRepository, VehicleDetailsMapper vehicleDetailsMapper) {
        this.vehicleDetailsRepository = vehicleDetailsRepository;
        this.vehicleDetailsMapper = vehicleDetailsMapper;
    }

    /**
     * Save a vehicleDetails.
     *
     * @param vehicleDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public VehicleDetailsDTO save(VehicleDetailsDTO vehicleDetailsDTO) {
        log.debug("Request to save VehicleDetails : {}", vehicleDetailsDTO);
        VehicleDetails vehicleDetails = vehicleDetailsMapper.toEntity(vehicleDetailsDTO);
        vehicleDetails = vehicleDetailsRepository.save(vehicleDetails);
        return vehicleDetailsMapper.toDto(vehicleDetails);
    }

    /**
     * Partially update a vehicleDetails.
     *
     * @param vehicleDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<VehicleDetailsDTO> partialUpdate(VehicleDetailsDTO vehicleDetailsDTO) {
        log.debug("Request to partially update VehicleDetails : {}", vehicleDetailsDTO);

        return vehicleDetailsRepository
            .findById(vehicleDetailsDTO.getId())
            .map(existingVehicleDetails -> {
                vehicleDetailsMapper.partialUpdate(existingVehicleDetails, vehicleDetailsDTO);

                return existingVehicleDetails;
            })
            .map(vehicleDetailsRepository::save)
            .map(vehicleDetailsMapper::toDto);
    }

    /**
     * Get all the vehicleDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VehicleDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VehicleDetails");
        return vehicleDetailsRepository.findAll(pageable).map(vehicleDetailsMapper::toDto);
    }

    /**
     * Get one vehicleDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VehicleDetailsDTO> findOne(Long id) {
        log.debug("Request to get VehicleDetails : {}", id);
        return vehicleDetailsRepository.findById(id).map(vehicleDetailsMapper::toDto);
    }

    /**
     * Delete the vehicleDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete VehicleDetails : {}", id);
        vehicleDetailsRepository.deleteById(id);
    }
}
