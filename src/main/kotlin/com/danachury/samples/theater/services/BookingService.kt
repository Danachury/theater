package com.danachury.samples.theater.services

import com.danachury.samples.theater.data.BookingRepository
import com.danachury.samples.theater.data.PerformanceRepository
import com.danachury.samples.theater.data.SeatRepository
import com.danachury.samples.theater.domain.Booking
import com.danachury.samples.theater.domain.Performance
import com.danachury.samples.theater.domain.Seat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.awt.print.Book

@Service
class BookingService {

    @Autowired
    lateinit var bookingRepository: BookingRepository

    @Autowired
    lateinit var seatRepository: SeatRepository

    @Autowired
    lateinit var performanceRepository: PerformanceRepository

    fun isSeatFree(seat: Seat, performance: Performance): Boolean =
        findBooking(seat, performance) == null

    fun findSeat(seatRow: Char, seatNum: Int): Seat? =
        seatRepository
            .findAll()
            .firstOrNull { it.row == seatRow && it.num == seatNum }

    fun findBooking(seat: Seat, performance: Performance): Booking? =
        bookingRepository
            .findAll()
            .firstOrNull { it.seat == seat && it.performance == performance }

    fun reserveSeat(seat: Seat, performance: Performance, customerName: String): Booking {
        val booking = Booking(0, customerName)
        booking.seat = seat
        booking.performance = performance
        bookingRepository.save(booking)
        return booking
    }
}
