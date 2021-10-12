package dev.poncio.SystemApps.InstantNotificationCenter.services;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.poncio.SystemApps.InstantNotificationCenter.dto.JobCreateDTO;
import dev.poncio.SystemApps.InstantNotificationCenter.dto.JobFinalizeDTO;
import dev.poncio.SystemApps.InstantNotificationCenter.dto.JobUpdateDTO;
import dev.poncio.SystemApps.InstantNotificationCenter.entities.Job;
import dev.poncio.SystemApps.InstantNotificationCenter.entities.JobUpdate;
import dev.poncio.SystemApps.InstantNotificationCenter.repositories.JobRepository;
import dev.poncio.SystemApps.InstantNotificationCenter.utils.AuthUtil;

@Service
public class JobService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private JobRepository repository;

	@Autowired
	private UserService userService;

	public Job createJob(JobCreateDTO createDTO) {
		try {
			Job job = new Job();
			job.setStatus(Job.JobStatus.RUNNING);
			job.setName(createDTO.getName());
			job.setDescription(createDTO.getDescription());
			job.setUser(this.userService.currentContextUser());
			return repository.saveAndFlush(job);
		} catch (Exception e) {
			logger.error("Failed to creating Job " + createDTO.getName(), e);
		}
		return null;
	}

	public Job finalizeJob(Long jobId, JobFinalizeDTO finalizeDTO) {
		return finalizeJob(jobId, finalizeDTO.isSuccess() ? Job.JobStatus.SUCCESS : Job.JobStatus.ERROR,
				finalizeDTO.getResultMessage());
	}

	public Job expireJob(Long jobId) {
		return finalizeJob(jobId, Job.JobStatus.EXPIRED, null);
	}

	private Job finalizeJob(Long jobId, Job.JobStatus status, String resultMessage) {
		try {
			Job job = repository.findOneByIdAndUser(jobId, this.userService.currentContextUser());
			if (job != null) {
				job.setEndDate(new Date());
				job.setStatus(status);
				job.setResultMessage(resultMessage);
				job.setFinished(Boolean.TRUE);
				return repository.saveAndFlush(job);
			}
		} catch (Exception e) {
			logger.error("Failed obtaining Job from DB, ID" + jobId, e);
		}
		return null;
	}

	public Job addUpdate(Long jobId, JobUpdateDTO updateDTO) {
		try {
			Job job = repository.findOneByIdAndUser(jobId, this.userService.currentContextUser());
			if (job != null) {
				job.getUpdates().add(JobUpdate.builder().percent(updateDTO.getPercent()).log(updateDTO.getLog())
						.job(job).secrets(AuthUtil.contextAuthCredentials()).build());
				return repository.saveAndFlush(job);
			}
		} catch (Exception e) {
			logger.error("Failed obtaining Job from DB, ID" + jobId, e);
		}
		return null;
	}

}
