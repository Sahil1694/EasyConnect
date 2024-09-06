package com.Smart_Contact_Manager.Smart_Contact_Manager.services;


import com.Smart_Contact_Manager.Smart_Contact_Manager.entities.User;

import java.util.List;
import java.util.Optional;


public interface UserService{
    User SaveUser(User user);
    Optional<User> getUserById(String id);
    Optional<User>  updateUser(User user);
    void deleteUser(String id);
    boolean isUserExist(String userId);
    boolean isUserExistByEmail(String email);
    List<User>getAllUsers();
    User getUserByEmail(String email);





}

