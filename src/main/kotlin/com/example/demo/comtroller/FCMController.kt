package com.example.demo.comtroller

import com.example.demo.repository.UsersRepository
import com.example.demo.util.FcmNotification
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class FCMController(private val usersRepository: UsersRepository) {

    @PostMapping("/register_fcm_key")
    fun put_fcm(@RequestBody payload: String): ResponseEntity<*> {

        var reqObj = JSONObject()
        val parser = JSONParser()
        val retObj = JSONObject()

        try {
            val obj = parser.parse(payload)
            reqObj = obj as JSONObject

            val phoneNum = reqObj["phoneNum"].toString()
            val registrationToken = reqObj["registrationToken"].toString()

            val users = usersRepository.findByPhonenum(phoneNum)
            users.fcmkey = registrationToken
            usersRepository.save(users)
            retObj.put("result", "Y")
        } catch (e: Exception) {
            retObj.put("result", "N")
        }
        return ResponseEntity(retObj.toJSONString(), HttpStatus.OK)
    }

    @PostMapping("/send_fcm_message")
    fun send_fcm(@RequestBody payload: String): ResponseEntity<*> {

        var reqObj = JSONObject()
        val parser = JSONParser()
        val retObj = JSONObject()
        System.out.println("send_fcm_message")
        try {
            val obj = parser.parse(payload)
            reqObj = obj as JSONObject
            val registrationToken = reqObj["registrationToken"].toString()
            val message = reqObj["message"].toString()

            val fcmNotification = FcmNotification()
            fcmNotification.sendMessage(registrationToken)

            retObj.put("result", "Y")
        } catch (e: Exception) {
            e.printStackTrace()
            retObj.put("result", "N")
        }
        return ResponseEntity(retObj.toJSONString(), HttpStatus.OK)
    }
}