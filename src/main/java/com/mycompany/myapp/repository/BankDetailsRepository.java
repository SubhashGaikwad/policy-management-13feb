package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.BankDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BankDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankDetailsRepository extends JpaRepository<BankDetails, Long>, JpaSpecificationExecutor<BankDetails> {}
