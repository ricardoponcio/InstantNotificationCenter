package dev.poncio.SystemApps.InstantNotificationCenter.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.poncio.SystemApps.InstantNotificationCenter.annotations.EnableSdkAccess;
import dev.poncio.SystemApps.InstantNotificationCenter.dto.JobCreateDTO;
import dev.poncio.SystemApps.InstantNotificationCenter.dto.JobFinalizeDTO;
import dev.poncio.SystemApps.InstantNotificationCenter.dto.JobUpdateDTO;
import dev.poncio.SystemApps.InstantNotificationCenter.dto.ResponseEntity;
import dev.poncio.SystemApps.InstantNotificationCenter.entities.Job;
import dev.poncio.SystemApps.InstantNotificationCenter.repositories.JobRepository;
import dev.poncio.SystemApps.InstantNotificationCenter.services.JobService;
import dev.poncio.SystemApps.InstantNotificationCenter.services.MessageService;

@Controller
@RestController
@RequestMapping("/job/")
public class JobController extends AbstractController {

    @Autowired
    private JobRepository repository;

    @Autowired
    private JobService jobService;

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private MessageService messageService;

    @PostMapping("/list")
    public ResponseEntity<List<Job>> listUsers() {
        try {
            return new ResponseEntity<List<Job>>().status(true).attach(repository.findAll())
                    .message(messageService.get("test.language"));
        } catch (Exception e) {
            return new ResponseEntity<List<Job>>().status(false).message(e.getMessage());
        }
    }

    @PostMapping("/update/{jobId}")
    public ResponseEntity<Job> updateJob(@PathVariable("jobId") Long jobId, @RequestBody JobUpdateDTO jobUpdate) {
        Job job = jobService.addUpdate(jobId, jobUpdate);
        if (job == null)
            return new ResponseEntity<Job>().message(this.messageService.get("job.id-nao-encontrato"));
        this.template.convertAndSend("/job/update", job);
        return new ResponseEntity<Job>().attach(job);
    }

    @PostMapping("/finalize/{jobId}")
    public ResponseEntity<Job> finalizeJob(@PathVariable("jobId") Long jobId, @RequestBody JobFinalizeDTO jobFinalize) {
        Job job = jobService.finalizeJob(jobId, jobFinalize);
        if (job == null)
            return new ResponseEntity<Job>().message(this.messageService.get("job.id-nao-encontrato"));
        this.template.convertAndSend("/job/update", job);
        return new ResponseEntity<Job>().attach(job);
    }

    @EnableSdkAccess
    @PostMapping("/create")
    public ResponseEntity<Job> createJob(@RequestBody JobCreateDTO jobCreate) {
        Job job = jobService.createJob(jobCreate);
        if (job == null)
            return new ResponseEntity<Job>().message(this.messageService.get("error.notfound"));
        this.template.convertAndSend("/job/update", job);
        return new ResponseEntity<Job>().attach(job);
    }

}
