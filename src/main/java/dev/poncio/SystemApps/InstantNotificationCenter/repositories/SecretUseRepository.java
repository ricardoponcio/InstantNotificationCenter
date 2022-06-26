package dev.poncio.SystemApps.InstantNotificationCenter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.poncio.SystemApps.InstantNotificationCenter.entities.SecretUse;

@Repository
public interface SecretUseRepository extends JpaRepository<SecretUse, Long> {

}
