package com.Smart_Contact_Manager.Smart_Contact_Manager.helpers;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message){
        super(message);
    }

    public ResourceNotFoundException(){
        super("Resource Not Found");
    }
}
