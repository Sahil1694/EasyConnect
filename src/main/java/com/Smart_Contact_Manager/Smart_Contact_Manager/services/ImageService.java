package com.Smart_Contact_Manager.Smart_Contact_Manager.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String uploadImage(MultipartFile contactImage , String filename);


    String getUrlFromPublicId(String publicId);
}
