package com.example.demo.comtroller

import com.example.demo.model.Users
import com.example.demo.repository.UsersRepository
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UsersController(private val usersRepository: UsersRepository) {

    @PostMapping("/put_user")
    fun put_user(@RequestBody payload: String): ResponseEntity<*> {

        var reqObj = JSONObject()
        val parser = JSONParser()
        val retObj = JSONObject()

        try {
            val obj = parser.parse(payload)
            reqObj = obj as JSONObject

            val user = reqObj["user"].toString()
            val babyName = reqObj["babyName"].toString()
            val babyWeek = reqObj["babyWeek"].toString()
            val phoneNum = reqObj["phoneNum"].toString()

            val users = Users()
            users.user = user
            users.babyname = babyName
            users.babyweek = babyWeek
            users.phonenum = phoneNum
            usersRepository.save(users)
            retObj.put("result", "Y")

        } catch (e : Exception){
            retObj.put("result", "N")
        }

        return ResponseEntity(retObj.toJSONString(), HttpStatus.OK)
    }


    @PostMapping("/get_user")
    fun get_user(@RequestBody payload: String): ResponseEntity<*> {

        var reqObj = JSONObject()
        val parser = JSONParser()
        val retObj = JSONObject()

        try {
            val obj = parser.parse(payload)
            reqObj = obj as JSONObject

            val phoneNum = reqObj["phoneNum"].toString()

            val users = usersRepository.findByPhonenum(phoneNum)

            retObj.put("user", users.user)
            retObj.put("babyName", users.babyname)
            retObj.put("babyWeek", users.babyweek)

        } catch (e : Exception){

        retObj.put("result", "N")
    }
        return ResponseEntity(retObj.toJSONString(), HttpStatus.OK)
    }

}