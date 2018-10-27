package com.example.demo.model

import javax.persistence.*


@Entity
@Table(name = "heartbeat_static")
class HeartbeatStatic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0
    @Column(name = "phonenum")
    var phonenum: String? = null
    @Column(name = "heartbeat")
    var heartbeat: String? = null
    @Column(name = "week")
    var week: String? = null
    @Column(name = "datetime")
    var datetime: String? = null
}