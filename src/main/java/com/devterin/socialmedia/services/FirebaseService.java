package com.devterin.socialmedia.services;

import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class FirebaseService {
    private final StorageClient storageClient;

    @Autowired
    public FirebaseService(StorageClient storageClient) {
        this.storageClient = storageClient;
    }

    public String uploadFileToFireBase(MultipartFile file, String directory) {
        String fileName = "post/"+ directory + UUID.randomUUID() + "-" + file.getOriginalFilename();
        try {
            // Check if the directory exists by trying to list its contents
            if (storageClient.bucket().get("post/" + directory) == null) {
                // Create a dummy file to simulate the folder
                storageClient.bucket().create("post/" + directory + ".keep", new byte[0]);
            }
            var blob = storageClient.bucket().create(fileName, file.getInputStream(), file.getContentType());
            blob.createAcl(com.google.cloud.storage.Acl.of(com.google.cloud.storage.Acl.User.ofAllUsers(), com.google.cloud.storage.Acl.Role.READER));
            return blob.getMediaLink();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
