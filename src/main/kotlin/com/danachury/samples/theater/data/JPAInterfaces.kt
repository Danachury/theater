package com.danachury.samples.theater.data

import com.danachury.samples.theater.domain.Booking
import com.danachury.samples.theater.domain.Seat
import com.danachury.samples.theater.domain.Performance
import org.springframework.data.jpa.repository.JpaRepository

interface SeatRepository : JpaRepository<Seat, Long>

interface PerformanceRepository : JpaRepository<Performance, Long>

interface BookingRepository : JpaRepository<Booking, Long>
