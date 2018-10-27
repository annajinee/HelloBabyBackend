package com.example.demo.model

import javax.persistence.*


@Entity
@Table(name = "mission")
class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var missionid: Int = 0
    @Column(name = "phonenum")
    var phonenum: String? = null
    @Column(name = "mission")
    var mission: String? = null
    @Column(name = "regtime")
    var regtime: String? = null
    @Column(name = "complete")
    var complete: String? = null
    @Column(name = "completedate")
    var completedate: String? = null
}