package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ProductType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Long>, JpaSpecificationExecutor<ProductType> {}
