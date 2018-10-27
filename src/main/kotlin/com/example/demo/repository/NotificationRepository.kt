package com.example.demo.repository

import com.example.demo.model.Notification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface NotificationRepository : JpaRepository<Notification, Int> {
    @Transactional(readOnly = true)
    fun findByPhonenum(@Param("phonenum") phonenum: String): List<Notification>
}