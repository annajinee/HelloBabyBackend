package com.example.demo.model

import javax.persistence.*


@Entity
@Table(name = "heartbeat_base")
class HeartbeatBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0
    @Column(name = "week")
    var week: String? = null
    @Column(name = "heartbeat_max")
    var heartbeat_max: String? = null
    @Column(name = "heartbeat_min")
    var heartbeat_min: String? = null
}