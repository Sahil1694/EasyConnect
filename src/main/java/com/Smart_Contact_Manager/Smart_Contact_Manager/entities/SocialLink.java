package com.Smart_Contact_Manager.Smart_Contact_Manager.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class SocialLink {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    private  String link;
    private String title;

    @ManyToOne
    private Contact contact;
}
