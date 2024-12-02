package com.Smart_Contact_Manager.Smart_Contact_Manager.services;

import com.Smart_Contact_Manager.Smart_Contact_Manager.entities.Contact;
import com.Smart_Contact_Manager.Smart_Contact_Manager.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContactService {
    //CRUD Operations
    Contact save(Contact contact);
    //Update
    Contact update(Contact contact);
    //Delete
    void delete(String id);
    //Get All by User
    Contact getById(String id);
    //Get All User
    List<Contact> getAll();
    //Search by Name
    Page<Contact> searchByName(String nameKeyword , int size , int page , String sortBy , String order , User user);

    Page<Contact> searchByEmail(String emailKeyword ,int size , int page , String sortBy , String order ,  User user);

    Page<Contact> searchByPhoneNumber(String phoneKeyword, int size , int page , String sortBy , String order, User user );


    List<Contact> getByUserId(String userId);
    Page<Contact> getByUser(User user, int page , int size , String sortField , String sortDirection);
}
