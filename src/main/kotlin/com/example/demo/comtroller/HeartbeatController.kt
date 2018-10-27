package com.example.demo.comtroller

import com.example.demo.model.Heartbeat
import com.example.demo.repository.*
import com.example.demo.util.FcmNotification
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.text.SimpleDateFormat

@RestController
class HeartbeatController(private val heartbeatRepository: HeartbeatRepository,
                          private val heartbeatStaticRepository: HeartbeatStaticRepository,
                          private val heartbeatBaseRepository: HeartbeatBaseRepository,
                          private val usersRepository: UsersRepository,
                          private val notificationRepository: NotificationRepository) {

    @PostMapping("/put_heartbeat")
    fun putHeartbeat(@RequestBody payload: String): ResponseEntity<*> {

        var reqObj = JSONObject()
        val parser = JSONParser()
        val retObj = JSONObject()

        try {
            val obj = parser.parse(payload)
            reqObj = obj as JSONObject

            val phoneNum = reqObj["phoneNum"].toString()
            val heartbeatper = reqObj["heartbeat"].toString()
            val temperature = reqObj["temperature"].toString()
            val humidity = reqObj["humidity"].toString()
            val nowTime = SimpleDateFormat("YYYY-MM-dd hh:mm:ss").format(java.util.Date(System.currentTimeMillis()))

            val heartbeat = Heartbeat()
            heartbeat.phonenum = phoneNum
            heartbeat.heartbeat = heartbeatper
            heartbeat.temperature = temperature
            heartbeat.humidity = humidity
            heartbeat.regtime = nowTime
            val mother = usersRepository.findByPhonenum(phoneNum)
            heartbeat.week = mother.babyweek
            heartbeatRepository.save(heartbeat)
            retObj.put("result", "Y")

            var week = mother.babyweek!!.toInt()
            if(week<6){
                week=6
            }else if(week>35){
                week=35
            }
            val heartbeatBase = heartbeatBaseRepository.findByWeek(week.toString())
            if (heartbeatper.toInt() > heartbeatBase.heartbeat_max!!.toInt() || heartbeatper.toInt() < heartbeatBase.heartbeat_min!!.toInt()) {

                val fcmNotification = FcmNotification()
                fcmNotification.sendMessage(mother.fcmkey!!, "심장박동수가 정상범위를 넘어갔어요!")
                val fatherFcm = usersRepository.findByMappingphone(phoneNum).fcmkey
                fcmNotification.sendMessage(fatherFcm!!, "심장박동수가 정상범위를 넘어갔어요!")
                NotificationContoller(notificationRepository).setNotifaction(phoneNum, "baby", "심장박동수가 정상범위를 넘어갔어요!", "")

            }

        } catch (e: Exception) {
            retObj.put("result", "N")
        }
        return ResponseEntity(retObj.toJSONString(), HttpStatus.OK)
    }

    @PostMapping("/get_heartbeat/avg")
    fun getHeartbeatAvg(@RequestBody payload: String): ResponseEntity<*> {

        var reqObj = JSONObject()
        val parser = JSONParser()
        val retObj = JSONObject()

        try {
            val obj = parser.parse(payload)
            reqObj = obj as JSONObject

            val phoneNum = reqObj["phoneNum"].toString()
            val datetime = reqObj["datetime"].toString()
            val type = reqObj["type"].toString()

            val heartbeatStatic = heartbeatStaticRepository.findByPhonenumAndDatetime(phoneNum, datetime)

            retObj.put("heartbeat", heartbeatStatic.heartbeat)

        } catch (e: Exception) {
            retObj.put("result", "N")
        }
        return ResponseEntity(retObj.toJSONString(), HttpStatus.OK)
    }


    @GetMapping("/get_heartbeat/avg/{week}")
    fun getHeartbeatAvgData(@PathVariable week: String): ResponseEntity<*> {

        val heartbeatBase = heartbeatBaseRepository.findByWeek(week)
        val retObj = JSONObject()
        retObj.put("heartbeat_max", heartbeatBase.heartbeat_max)
        retObj.put("heartbeat_min", heartbeatBase.heartbeat_min)

        return ResponseEntity(retObj.toJSONString(), HttpStatus.OK)
    }

}