package com.example.demo.repository

import com.example.demo.model.Mission
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface MissionRepository : JpaRepository<Mission, Int> {
    @Transactional(readOnly = true)
    fun findByMissionid(@Param("id") id: Int): Mission
    @Transactional(readOnly = true)
    fun findByPhonenum(@Param("phonenum") phonenum: String): List<Mission>

}