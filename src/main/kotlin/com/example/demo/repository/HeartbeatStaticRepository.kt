package com.example.demo.repository

import com.example.demo.model.HeartbeatStatic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface HeartbeatStaticRepository : JpaRepository<HeartbeatStatic, Int> {
    @Transactional(readOnly = true)
    fun findByPhonenumAndDatetime(@Param("phonenum") phonenum: String,
                                  @Param("datetime") datetime: String): HeartbeatStatic
}