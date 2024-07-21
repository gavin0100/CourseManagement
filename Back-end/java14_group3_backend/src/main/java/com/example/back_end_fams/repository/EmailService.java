package com.example.back_end_fams.repository;

import com.example.back_end_fams.model.entity.EmailDetails;

public interface EmailService {
    String sendSimpleMail(EmailDetails details);

    // Method
    // To send an email with attachment
    String sendMailWithAttachment(EmailDetails details);
}
