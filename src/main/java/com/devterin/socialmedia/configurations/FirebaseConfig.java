package com.devterin.socialmedia.configurations;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
@Slf4j
public class FirebaseConfig {
    @Value("${firebase.credentials.path}")
    private String credentialPath;

    @Value("${firebase.storageBucket.name}")
    private String storageBucketName;
    @Bean
    public FirebaseApp initializeFirebase() throws IOException {
        if (FirebaseApp.getApps().isEmpty()) {
            FileInputStream serviceAccount = new FileInputStream(credentialPath);

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setStorageBucket(storageBucketName)
                    .build();
            log.info("Firebase initialization completed ...");
            return FirebaseApp.initializeApp(options);
        } else {
            return FirebaseApp.getInstance();
        }
    }
    @Bean
    public StorageClient storageClient(FirebaseApp firebaseApp) {
        return StorageClient.getInstance(firebaseApp);
    }
}
