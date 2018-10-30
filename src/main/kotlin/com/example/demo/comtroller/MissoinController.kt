package com.example.demo.comtroller

import com.example.demo.model.Mission
import com.example.demo.repository.MissionRepository
import com.example.demo.repository.NotificationRepository
import com.example.demo.repository.UsersRepository
import com.example.demo.util.FcmNotification
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.text.SimpleDateFormat


@RestController
class MissoinController(private val usersRepository: UsersRepository,
                        private val missionRepository: MissionRepository,
                        private val notificationRepository: NotificationRepository) {

    @PostMapping("/request_mission")
    fun requestMission(@RequestBody payload: String): ResponseEntity<*> {

        var reqObj = JSONObject()
        val parser = JSONParser()
        val retObj = JSONObject()

        try {
            val obj = parser.parse(payload)
            reqObj = obj as JSONObject

            val phoneNum = reqObj["phoneNum"].toString()
            val mission = reqObj["mission"].toString()

            val nowTime = SimpleDateFormat("YYYY-MM-dd hh:mm:ss").format(java.util.Date(System.currentTimeMillis()))

            val missionObj = Mission();
            missionObj.phonenum = phoneNum
            missionObj.regtime = nowTime
            missionObj.mission = mission
            missionObj.complete = "N"
            missionRepository.save(missionObj)

            val father = usersRepository.findByMappingphone(phoneNum)

            val fcmNotification = FcmNotification()
            fcmNotification.sendMessage(father.fcmkey!!, mission, "mission")

            NotificationContoller(notificationRepository).setNotifaction(father.phonenum!!, "mission", mission, "")

            retObj.put("result", "Y")

        } catch (e: Exception) {
            retObj.put("result", "N")
        }
        return ResponseEntity(retObj.toJSONString(), HttpStatus.OK)
    }

    @GetMapping("/get_mission_list/{phoneNum}")
    fun getMissionList(@PathVariable phoneNum: String): ResponseEntity<*> {

        val retObj = JSONObject()

        try {
            val missionList = missionRepository.findByPhonenum(phoneNum)
            val missionArry = JSONArray()
            for (mission in missionList) {
                var missionObj = JSONObject()
                missionObj.put("mission", mission.mission)
                missionObj.put("missionId", mission.missionid)
                missionArry.add(missionObj)
            }

            retObj.put("data", missionArry)
            retObj.put("result", "Y")

        } catch (e: Exception) {
            retObj.put("result", "N")
        }
        return ResponseEntity(retObj.toJSONString(), HttpStatus.OK)
    }

    @GetMapping("/get_mission/{missionId}")
    fun getMission(@PathVariable missionId: Int): ResponseEntity<*> {

        val retObj = JSONObject()

        try {
            val mission = missionRepository.findByMissionid(missionId)
            retObj.put("mission", mission.mission)
            retObj.put("missionId", mission.missionid)

        } catch (e: Exception) {
            retObj.put("result", "N")
        }
        return ResponseEntity(retObj.toJSONString(), HttpStatus.OK)
    }

    @GetMapping("/complete_mission/{missionId}")
    fun completeMission(@PathVariable missionId: Int): ResponseEntity<*> {

        val retObj = JSONObject()

        try {
            val mission = missionRepository.findByMissionid(missionId)
            val nowTime = SimpleDateFormat("YYYY-MM-dd hh:mm:ss").format(java.util.Date(System.currentTimeMillis()))

            mission.complete = "Y"
            mission.completedate = nowTime
            missionRepository.save(mission)
            retObj.put("result", "Y")

            val user = usersRepository.findByPhonenum(mission.phonenum!!)
            val fcmNotification = FcmNotification()
            fcmNotification.sendMessage(user.fcmkey!!, "아빠 미션완료", "mission")

            NotificationContoller(notificationRepository).setNotifaction(user.phonenum!!, "mission", "아빠 미션완료", "")

        } catch (e: Exception) {
            retObj.put("result", "N")
        }
        return ResponseEntity(retObj.toJSONString(), HttpStatus.OK)
    }
}