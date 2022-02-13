package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Rider;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Rider entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RiderRepository extends JpaRepository<Rider, Long>, JpaSpecificationExecutor<Rider> {}
