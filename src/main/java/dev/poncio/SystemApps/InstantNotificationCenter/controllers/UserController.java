package dev.poncio.SystemApps.InstantNotificationCenter.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.poncio.SystemApps.InstantNotificationCenter.entities.User;
import dev.poncio.SystemApps.InstantNotificationCenter.repositories.UserRepository;

@Controller
@RestController
@RequestMapping("/user/")
public class UserController {
    
    @Autowired
    private UserRepository repository;

    @GetMapping("/list")
	public ResponseEntity<List<User>> listUsers() {
        try {
            return ResponseEntity.ok().body(repository.findAll());
        } catch(Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

}
