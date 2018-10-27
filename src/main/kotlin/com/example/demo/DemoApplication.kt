package com.example.demo

import com.example.demo.util.FcmNotification
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
    val fcmNotification = FcmNotification()
    fcmNotification.initilizeFcm()
    runApplication<DemoApplication>(*args)
}
