package com.example.demo.util;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FcmNotification {

    public void sendMessage(String registrationToken) {
        Message message = Message.builder()
                .putData("body", "아빠 딸끼!!!!!!!!!!!!!!!!!!")
                .putData("title", "title")
                .putData("type", "message")
                .putData("message", "hihihihihi")
                .setToken(registrationToken)
                .build();


        String response = null;
        try {
            response = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
        System.out.println("Success :" + response);
    }

    public void initilizeFcm() {
        FileInputStream serviceAccount = null;
        try {
            serviceAccount = new FileInputStream("/Users/annakim/Desktop/HelloBabyBackend/src/main/resources/serviceAccountKey.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        FirebaseOptions options = null;
        try {
            options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://hellobaby-3af86.firebaseio.com")
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FirebaseApp.initializeApp(options);
    }
}
