package com.example.demo.repository

import com.example.demo.model.HeartbeatBase
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface HeartbeatBaseRepository : JpaRepository<HeartbeatBase, Int> {
    @Transactional(readOnly = true)
    fun findByWeek(@Param("week") week: String): HeartbeatBase
}