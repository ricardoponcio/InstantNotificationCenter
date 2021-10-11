package dev.poncio.SystemApps.InstantNotificationCenter.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.poncio.SystemApps.InstantNotificationCenter.dto.JobUpdateDTO;
import dev.poncio.SystemApps.InstantNotificationCenter.entities.Job;
import dev.poncio.SystemApps.InstantNotificationCenter.entities.JobUpdate;
import dev.poncio.SystemApps.InstantNotificationCenter.repositories.JobRepository;

@Service
public class JobService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JobRepository repository;

    public Job addUpdate(Long jobId, JobUpdateDTO updateDTO) {
		try {
			Job job = repository.findById(jobId).get();
			if (job != null) {
				job.getUpdates().add(
					JobUpdate.builder()
						.percent(updateDTO.getPercent())
						.log(updateDTO.getLog())
						.job(job)
						.build()
				);
				return repository.saveAndFlush(job);
			}
		} catch (Exception e) {
			logger.error("Failed obtaining Job from DB, ID" + jobId, e);
		}
		return null;
	}
    
}
