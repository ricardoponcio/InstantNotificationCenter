package dev.poncio.SystemApps.InstantNotificationCenter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.poncio.SystemApps.InstantNotificationCenter.dto.ResponseEntity;

@Controller
@RestController
@RequestMapping("/sdk/")
public class SdkController extends AbstractController {

    @PostMapping("/test")
    public ResponseEntity<Object> Test() {
        return new ResponseEntity<>().status(true).message(messageService.get("test.language"));
    }

}
