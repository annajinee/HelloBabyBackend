package com.example.demo.repository

import com.example.demo.model.Users
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface UsersRepository : JpaRepository<Users, Int> {
    @Transactional(readOnly = true)
    fun findByPhonenum(@Param("phonenum") phonenum: String): Users
    @Transactional(readOnly = true)
    fun findByMappingphone(@Param("mappingphone") phonenum: String): Users
}