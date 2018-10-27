package com.example.demo.comtroller

import com.example.demo.model.Heartbeat
import com.example.demo.repository.HeartbeatRepository
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class HeartbeatController(private val heartbeatRepository: HeartbeatRepository) {

    @PostMapping("/put_heartbeat")
    fun put_user(@RequestBody payload: String): ResponseEntity<*> {

        var reqObj = JSONObject()
        val parser = JSONParser()
        val retObj = JSONObject()

        try {
            val obj = parser.parse(payload)
            reqObj = obj as JSONObject

            val phoneNum = reqObj["phoneNum"].toString()
            val heartbeatper = reqObj["heartbeat"].toString()
            val type = reqObj["type"].toString()

            val heartbeat = Heartbeat()
            heartbeat.phonenum = phoneNum
            heartbeat.heartbeat = heartbeatper
            heartbeat.type = type
            heartbeat.phonenum = phoneNum
            heartbeatRepository.save(heartbeat)
            retObj.put("result", "Y")

//            val fcmNotification = FcmNotification()
//            fcmNotification.pushFCMNotification(phoneNum, "HelloBaby, 엇! 좀 이상해요?", "박동수가 "+heartbeat +" 로 정상범위를 벗어났습니다")

        } catch (e : Exception){
            retObj.put("result", "N")
        }
        return ResponseEntity(retObj.toJSONString(), HttpStatus.OK)
    }

}