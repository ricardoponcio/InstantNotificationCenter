package dev.poncio.SystemApps.InstantNotificationCenter.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.poncio.SystemApps.InstantNotificationCenter.dto.ResponseEntity;
import dev.poncio.SystemApps.InstantNotificationCenter.entities.Job;
import dev.poncio.SystemApps.InstantNotificationCenter.repositories.JobRepository;

@Controller
@RestController
@RequestMapping("/job/")
public class JobController extends AbstractController {

    @Autowired
    private JobRepository repository;

    @PostMapping("/list")
    public ResponseEntity<List<Job>> listUsers() {
        try {
            return new ResponseEntity<List<Job>>().status(true).attach(repository.findAll()).message(messageService.get("test.language"));
        } catch (Exception e) {
            return new ResponseEntity<List<Job>>().status(false).message(e.getMessage());
        }
    }

}
