package com.example.demo.repository

import com.example.demo.model.Heartbeat
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface HeartbeatRepository : JpaRepository<Heartbeat, Int> {
}