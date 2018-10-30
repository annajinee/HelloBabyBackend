package com.example.demo.util

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingException
import com.google.firebase.messaging.Message
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException

class FcmNotification {

    fun sendMessage(registrationToken: String, msg: String, type:String) {
        val message = Message.builder()
                .putData("body", msg)
                .putData("title", "아빠~좋은 아침:D")
                .putData("type", type)
                .putData("message", msg)
                .setToken(registrationToken)
                .build()

        var response: String? = null
        try {
            response = FirebaseMessaging.getInstance().send(message)
        } catch (e: FirebaseMessagingException) {
            e.printStackTrace()
        }

        println("Success :" + response!!)
    }

    fun initilizeFcm() {
        var serviceAccount: FileInputStream? = null
        try {
            serviceAccount = FileInputStream("/home/HelloBaby/serviceAccountKey.json")
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

        var options: FirebaseOptions? = null
        try {
            options = FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount!!))
                    .setDatabaseUrl("https://hellobaby-3af86.firebaseio.com")
                    .build()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        FirebaseApp.initializeApp(options!!)
    }
}
