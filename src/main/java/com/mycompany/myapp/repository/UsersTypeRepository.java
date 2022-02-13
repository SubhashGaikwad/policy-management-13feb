package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.UsersType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the UsersType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsersTypeRepository extends JpaRepository<UsersType, Long>, JpaSpecificationExecutor<UsersType> {}
