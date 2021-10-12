package dev.poncio.SystemApps.InstantNotificationCenter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.poncio.SystemApps.InstantNotificationCenter.dto.ResponseEntity;
import dev.poncio.SystemApps.InstantNotificationCenter.dto.SecretsCreateDTO;
import dev.poncio.SystemApps.InstantNotificationCenter.entities.Secrets;
import dev.poncio.SystemApps.InstantNotificationCenter.services.SecretsService;

@Controller
@RestController
@RequestMapping("/secrets/")
public class SecretsController extends AbstractController {

    @Autowired
    private SecretsService secretsService;

    @PostMapping("/create")
    public ResponseEntity<Secrets> createSecret(@RequestBody SecretsCreateDTO createDTO) {
        try {
            return new ResponseEntity<Secrets>().status(true).attach(this.secretsService.generateNewSecret(createDTO));
        } catch (Exception e) {
            return new ResponseEntity<Secrets>().status(false).message(e.getMessage());
        }
    }

}
