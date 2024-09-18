package com.Smart_Contact_Manager.Smart_Contact_Manager.services;

import com.Smart_Contact_Manager.Smart_Contact_Manager.entities.Contact;
import com.Smart_Contact_Manager.Smart_Contact_Manager.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContactService {
    Contact save(Contact contact);
    Contact update(Contact contact);
    void delete(String id);
    Contact getById(String id);

    List<Contact> getAll();

    Page<Contact> searchByName(String nameKeyword , int size , int page , String sortBy , String order , User user);

    Page<Contact> searchByEmail(String emailKeyword ,int size , int page , String sortBy , String order ,  User user);

    Page<Contact> searchByPhoneNumber(String phoneKeyword, int size , int page , String sortBy , String order, User user );


    List<Contact> getByUserId(String userId);
    Page<Contact> getByUser(User user, int page , int size , String sortField , String sortDirection);
}
