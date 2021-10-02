package dev.poncio.SystemApps.InstantNotificationCenter.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.poncio.SystemApps.InstantNotificationCenter.entities.Job;
import dev.poncio.SystemApps.InstantNotificationCenter.entities.User;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    
    List<Job> findAll();

    List<Job> findAllByUser(User user);

}
