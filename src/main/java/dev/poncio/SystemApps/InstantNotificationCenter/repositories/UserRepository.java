package dev.poncio.SystemApps.InstantNotificationCenter.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.poncio.SystemApps.InstantNotificationCenter.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    List<User> findAll();

}
