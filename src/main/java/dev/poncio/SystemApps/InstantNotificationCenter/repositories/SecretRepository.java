package dev.poncio.SystemApps.InstantNotificationCenter.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.poncio.SystemApps.InstantNotificationCenter.entities.Secrets;
import dev.poncio.SystemApps.InstantNotificationCenter.entities.User;

@Repository
public interface SecretRepository extends JpaRepository<Secrets, Long> {
    
    List<Secrets> findAllByUserAndActiveTrue(User user);

    Secrets findOneBySecretAndActiveTrue(String token);

    Secrets findOneByIdAndUserAndActiveTrue(Long id, User user);

}
