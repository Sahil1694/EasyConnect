package com.Smart_Contact_Manager.Smart_Contact_Manager.services;

import org.springframework.stereotype.Service;


public interface EmailService {

    void sendEmail(String to, String subject, String body);


    void sendEmailWithHtml();


    void sendEmailWithAttachment();
}
