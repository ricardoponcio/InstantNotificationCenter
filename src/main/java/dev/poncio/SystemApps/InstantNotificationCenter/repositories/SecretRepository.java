package dev.poncio.SystemApps.InstantNotificationCenter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.poncio.SystemApps.InstantNotificationCenter.entities.Secrets;
import dev.poncio.SystemApps.InstantNotificationCenter.entities.User;

@Repository
public interface SecretRepository extends JpaRepository<Secrets, Long> {
    
    Secrets findOneBySecretAndActiveTrue(String token);

    Secrets findOneByIdAndUserAndActiveTrue(Long id, User user);

}
