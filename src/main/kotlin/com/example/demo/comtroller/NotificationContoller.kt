package com.example.demo.comtroller

import com.example.demo.model.Notification
import com.example.demo.repository.NotificationRepository
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.text.SimpleDateFormat


@RestController
class NotificationContoller(private val notificationRepository: NotificationRepository) {

    @GetMapping("/get_notification_list/{phoneNum}")
    fun getMissionList(@PathVariable phoneNum: String): ResponseEntity<*> {

        val retObj = JSONObject()

        try {
            val notificationList = notificationRepository.findByPhonenum(phoneNum)
            val missionArry = JSONArray()
            for(notification in notificationList){
                var missionObj = JSONObject()
                missionObj.put("type", notification.type)
                missionObj.put("text", notification.text)
                missionObj.put("datetime", notification.datetime)
                missionArry.add(missionObj)
            }

            retObj.put("data", missionArry)
            retObj.put("result", "Y")

        } catch (e: Exception) {
            retObj.put("result", "N")
        }
        return ResponseEntity(retObj.toJSONString(), HttpStatus.OK)
    }

    fun setNotifaction(phoneNum: String, type:String, text:String, place:String) {

        val nowTime = SimpleDateFormat("YYYY-MM-dd hh:mm").format(java.util.Date(System.currentTimeMillis()))

        val notification = Notification()
        notification.datetime = nowTime
        notification.type = type
        notification.text = text
        notification.phonenum = phoneNum
        notificationRepository.save(notification)
    }
}