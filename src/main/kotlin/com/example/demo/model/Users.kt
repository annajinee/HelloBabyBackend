package com.example.demo.model

import javax.persistence.*


@Entity
@Table(name = "users")
class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0
    @Column(name = "babyname")
    var babyname: String? = null
    @Column(name = "babyweek")
    var babyweek: String? = null
    @Column(name = "user")
    var user: String? = null
    @Column(name = "phonenum")
    var phonenum: String? = null
    @Column(name = "fcmkey")
    var fcmkey: String? = null
    @Column(name = "mappingphone")
    var mappingphone: String? = null
}