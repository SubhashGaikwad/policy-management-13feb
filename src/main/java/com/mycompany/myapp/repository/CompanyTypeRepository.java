package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CompanyType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CompanyType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyTypeRepository extends JpaRepository<CompanyType, Long>, JpaSpecificationExecutor<CompanyType> {}
