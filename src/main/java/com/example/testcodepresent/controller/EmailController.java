package com.example.testcodepresent.controller;

import com.example.testcodepresent.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    @Autowired
    EmailService emailService;

    @GetMapping("/email/send")
    public String sendEmail(@RequestParam String email, @RequestParam String context) {
        emailService.sendEmail(email, context);
        return "Sent Email Successfully";
    }

}
