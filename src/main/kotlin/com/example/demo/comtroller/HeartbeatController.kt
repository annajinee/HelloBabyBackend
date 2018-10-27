package com.example.demo.comtroller

import com.example.demo.model.Heartbeat
import com.example.demo.repository.HeartbeatBaseRepository
import com.example.demo.repository.HeartbeatRepository
import com.example.demo.repository.HeartbeatStaticRepository
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
class HeartbeatController(private val heartbeatRepository: HeartbeatRepository,
                          private val heartbeatStaticRepository: HeartbeatStaticRepository,
                          private val heartbeatBaseRepository: HeartbeatBaseRepository,
                          private val usersRepository: UsersRepository) {

    @PostMapping("/put_heartbeat")
    fun put_heartbeat(@RequestBody payload: String): ResponseEntity<*> {

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

            val users = usersRepository.findByPhonenum(phoneNum)
            val heartbeatBase = heartbeatBaseRepository.findByWeek(users[0].babyweek!!)
            if(heartbeatper.toInt()> heartbeatBase.heartbeat_max!!.toInt() || heartbeatper.toInt()< heartbeatBase.heartbeat_min!!.toInt()){
                for(user in users){
                    val fcmNotification = FcmNotification()
                    fcmNotification.sendMessage(user.fcmkey!!, "심장박동수가 정상범위를 넘어갔어요!")
                }
            }

        } catch (e : Exception){
            retObj.put("result", "N")
        }
        return ResponseEntity(retObj.toJSONString(), HttpStatus.OK)
    }

    @PostMapping("/get_heartbeat/avg")
    fun get_heartbeat_avg(@RequestBody payload: String): ResponseEntity<*> {

        var reqObj = JSONObject()
        val parser = JSONParser()
        val retObj = JSONObject()

        try {
            val obj = parser.parse(payload)
            reqObj = obj as JSONObject

            val phoneNum = reqObj["phoneNum"].toString()
            val datetime = reqObj["datetime"].toString()
            val type = reqObj["type"].toString()

            val heartbeatStatic = heartbeatStaticRepository.findByPhonenumAndDatetimeAndType(phoneNum, datetime, type)

            retObj.put("heartbeat", heartbeatStatic.heartbeat)

        } catch (e : Exception){
            retObj.put("result", "N")
        }
        return ResponseEntity(retObj.toJSONString(), HttpStatus.OK)
    }


}