package com.Smart_Contact_Manager.Smart_Contact_Manager.services.impl;


import com.Smart_Contact_Manager.Smart_Contact_Manager.entities.User;
import com.Smart_Contact_Manager.Smart_Contact_Manager.helpers.AppConstants;
import com.Smart_Contact_Manager.Smart_Contact_Manager.helpers.Helper;
import com.Smart_Contact_Manager.Smart_Contact_Manager.helpers.ResourceNotFoundException;
import com.Smart_Contact_Manager.Smart_Contact_Manager.repositories.UserRepo;
import com.Smart_Contact_Manager.Smart_Contact_Manager.services.EmailService;
import com.Smart_Contact_Manager.Smart_Contact_Manager.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;


    @Override
    public User SaveUser(User user) {
         //user id have to generate
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRoleList(List.of(AppConstants.ROLE_USER));

        //password encode


        String emailToken = UUID.randomUUID().toString();
        user.setEmailToken(emailToken);
        User savedUser =  userRepo.save(user);
        String emailLink = Helper.getLinkForEmailVerification(emailToken);

        emailService.sendEmail(savedUser.getEmail() , "Email Verification" , emailLink);


        return savedUser;
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {
       //Update
        User user2 = userRepo.findById(user.getUserId()).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
        user2.setName(user.getName());
        user2.setUserId(user.getUserId());
        user2.setPassword(user.getPassword());
        user2.setAbout(user.getAbout());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setProfilePic(user.getProfilePic());
        user2.setEnabled(user.isEnabled());
        user2.setEmailVerified(user.isEmailVerified());
        user2.setPhoneVerified(user.isPhoneVerified());
        user2.setProvider(user.getProvider());
        user2.setProviderUserId(user.getProviderUserId());


        User save = userRepo.save(user2);

       return Optional.ofNullable(save);
    }

    @Override
    public void deleteUser(String id) {
        User user2 = userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
        userRepo.delete(user2);
    }

    @Override
    public boolean isUserExist(String userId) {
        User user2 = userRepo.findById(userId).orElse(null);
        return user2 != null ? true : false;
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        User user2 = userRepo.findByEmail(email).orElse(null);
        return user2 != null ? true : false;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
       return userRepo.findByEmail(email).orElse(null);
    }
}
