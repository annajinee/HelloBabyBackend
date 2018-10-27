//package com.example.demo.util
//
//import org.json.simple.JSONObject
//
//import java.io.OutputStreamWriter
//import java.net.HttpURLConnection
//import java.net.URL
//
//
//class FcmNotification {
//
//    companion object {
//        val AUTH_KEY_FCM = "AIzaSyCN1D9Qjlpz4EuxtXABx4LPZhwSroMygXs"
//        val API_URL_FCM = "https://fcm.googleapis.com/fcm/send"
//    }
//
//    @Throws(Exception::class)
//    fun pushFCMNotification(DeviceIdKey: String, title: String, message: String) {
//
//        val info = JSONObject().apply {
//            put("title", title)
//            put("body", message)
//            put("type", "message")
//        }
//
//        val json = JSONObject().apply {
//            put("to", DeviceIdKey.trim { it <= ' ' })
//            put("data", info)
//        }
//
//        println(json.toString())
//
//        val url = URL(API_URL_FCM)
//
//        val conn = (url.openConnection() as HttpURLConnection).apply {
//            useCaches = false
//            doInput = true
//            doOutput = true
//
//            requestMethod = "POST"
//            setRequestProperty("Authorization", "key=$AUTH_KEY_FCM")
//            setRequestProperty("Content-Type", "application/json")
//        }
//
//        OutputStreamWriter(conn.outputStream).let {
//            it.write(json.toString())
//            it.flush()
//        }
//
//        conn.inputStream
//    }
//
//}