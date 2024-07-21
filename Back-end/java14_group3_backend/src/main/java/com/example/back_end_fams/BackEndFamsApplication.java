package com.example.back_end_fams;

import com.example.back_end_fams.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class BackEndFamsApplication {
    @Autowired
    private EmailSenderService service;
    public static void main(String[] args) {
        SpringApplication.run(BackEndFamsApplication.class, args);
    }
}
