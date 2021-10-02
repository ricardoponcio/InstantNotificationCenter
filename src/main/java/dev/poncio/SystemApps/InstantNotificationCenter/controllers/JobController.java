package dev.poncio.SystemApps.InstantNotificationCenter.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.poncio.SystemApps.InstantNotificationCenter.entities.Job;
import dev.poncio.SystemApps.InstantNotificationCenter.repositories.JobRepository;

@Controller
@RestController
@RequestMapping("/job/")
public class JobController {

    @Autowired
    private JobRepository repository;

    @PostMapping("/list")
    public ResponseEntity<List<Job>> listUsers() {
        try {
            return ResponseEntity.ok().body(repository.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

}
