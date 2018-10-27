package com.example.demo.model

import javax.persistence.*


@Entity
@Table(name = "notification")
class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var missionid: Int = 0
    @Column(name = "type")
    var type: String? = null
    @Column(name = "text")
    var text: String? = null
    @Column(name = "datetime")
    var datetime: String? = null
    @Column(name = "phonenum")
    var phonenum: String? = null
}