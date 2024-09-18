package com.Smart_Contact_Manager.Smart_Contact_Manager.repositories;

import com.Smart_Contact_Manager.Smart_Contact_Manager.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepo extends JpaRepository< User, String> {
    //extra methods db related
    //custom query
    //custom finder mehtos
    Optional<User> findByEmail(String email);
    Optional<User>findByEmailAndPassword(String email, String password);
    Optional<User>findByEmailToken(String id);

}
