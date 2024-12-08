package com.example.banking.repository;


import com.example.banking.entities.LocalClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocalClientRepository extends JpaRepository<LocalClient,Long> {
    Optional<LocalClient> findByFineractClientId(Long fineractClientId);

    Optional<LocalClient> findByEmailAddress(String emailAdress);

    Optional<LocalClient> findByMobileNo(String mobileNo);


}
