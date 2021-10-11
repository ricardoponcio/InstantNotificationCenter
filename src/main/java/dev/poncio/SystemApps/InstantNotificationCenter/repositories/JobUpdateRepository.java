package dev.poncio.SystemApps.InstantNotificationCenter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.poncio.SystemApps.InstantNotificationCenter.entities.JobUpdate;

@Repository
public interface JobUpdateRepository extends JpaRepository<JobUpdate, Long> {

}
