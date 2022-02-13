package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.VehicleClass;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the VehicleClass entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehicleClassRepository extends JpaRepository<VehicleClass, Long>, JpaSpecificationExecutor<VehicleClass> {}
