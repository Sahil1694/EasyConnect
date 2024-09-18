package com.Smart_Contact_Manager.Smart_Contact_Manager;

import com.Smart_Contact_Manager.Smart_Contact_Manager.services.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class TestingTest {

    @Autowired
    private EmailService emailService;

    @Test
    public void sendEmailTest(){
        emailService.sendEmail("sahilkhilari29@gmail.com" ,"Testing Mail","Hii");
    }
}
