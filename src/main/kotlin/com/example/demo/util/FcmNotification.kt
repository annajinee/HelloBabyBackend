package com.example.demo.util

import org.json.simple.JSONObject

import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL


class FcmNotification {

    companion object {
        val AUTH_KEY_FCM = ""
        val API_URL_FCM = "https://fcm.googleapis.com/fcm/send"
    }

    @Throws(Exception::class)
    fun pushFCMNotification(DeviceIdKey: String, title: String, message: String) {

        val authKey = AUTH_KEY_FCM   // You FCM AUTH key
        val FMCurl = API_URL_FCM

        val url = URL(FMCurl)
        val conn = url.openConnection() as HttpURLConnection

        conn.useCaches = false
        conn.doInput = true
        conn.doOutput = true

        conn.requestMethod = "POST"
        conn.setRequestProperty("Authorization", "key=$authKey")
        conn.setRequestProperty("Content-Type", "application/json")

        val json = JSONObject()
        json["to"] = DeviceIdKey.trim { it <= ' ' }
        val info = JSONObject()
        info["title"] = title
        info["body"] = message
        //        info.put("image", "");
        info["type"] = "message"
        json["data"] = info
        println(json.toString())
        val wr = OutputStreamWriter(conn.outputStream)
        wr.write(json.toString())
        wr.flush()
        conn.inputStream
    }

}