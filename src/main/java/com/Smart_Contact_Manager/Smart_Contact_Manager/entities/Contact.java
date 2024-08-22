package com.Smart_Contact_Manager.Smart_Contact_Manager.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Contact {

    @Id
    private String id;
    private String name;
    private String email;
    private String phonenumber;
    private String address;
    private String picture;
    @Column(length = 1000)
    private String description;
    private boolean favorite = false;

    private String websiteLink;
    private String linkedInLink;
    //private List<String> SocialLink  = new ArrayList<>();

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "contact" , cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    private List<SocialLink> links = new ArrayList<>();



}
