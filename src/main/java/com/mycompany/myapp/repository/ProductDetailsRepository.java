package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ProductDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductDetailsRepository extends JpaRepository<ProductDetails, Long>, JpaSpecificationExecutor<ProductDetails> {}
