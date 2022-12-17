package com.example.testcodepresent.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
    public void sendEmail(String email, String context) {
        System.out.println(makeContext(email));
    }

    private String makeContext(String email) {
        return "[Program sent email to " + email;
    }
}
