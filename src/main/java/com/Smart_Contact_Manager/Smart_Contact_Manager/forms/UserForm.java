package com.Smart_Contact_Manager.Smart_Contact_Manager.forms;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserForm {

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 20, message = "Name must be between 3 to 20 characters")
    private String name;
    @Email(message = "Please enter a valid email")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min =3 , message = "Password must be atleast 3 characters")
    private String password;

    @NotBlank(message = "About is required")
    private String about;

    @Size(min = 8 , max = 12 , message = "Phone number must be between 8 to 12 characters")
    private String phoneNumber;


}
