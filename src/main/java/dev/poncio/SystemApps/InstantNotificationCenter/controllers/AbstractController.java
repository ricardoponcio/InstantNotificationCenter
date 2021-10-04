package dev.poncio.SystemApps.InstantNotificationCenter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import dev.poncio.SystemApps.InstantNotificationCenter.services.MessageService;

@Controller
public class AbstractController {
    
    @Autowired
	protected MessageService messageService;

}
