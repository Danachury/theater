package com.danachury.samples.theater.services

import com.danachury.samples.theater.data.BookingRepository
import com.danachury.samples.theater.domain.Booking
import com.danachury.samples.theater.domain.View
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ReportsService {

    @Autowired
    lateinit var bookingRepository: BookingRepository

    fun allBookings(): String {
        val bookings = bookingRepository.findAll()
        val htmlBookings = bookings.joinToString(transform = ::htmlBookings)
        return "${reportHeader()}${htmlBookings}${reportFooter()}"
    }

    fun premiumBookings(): String {
        val bookings = bookingRepository.findAll()
            .filter { it.seat.price >= View.RESTRICTED_VIEW.price }
        val htmlBookings = bookings.joinToString(transform = ::htmlBookings)
        return "${reportHeader()}${htmlBookings}${reportFooter()}"
    }

    private fun htmlBookings(booking: Booking) =
        """
            <tr>
                <td>${booking.performance.title}</td><td>${booking.seat}</td><td>${booking.customerName}</td>
            </tr>
        """.trimIndent()

    private fun reportHeader() =
        """
            <table><tr><th>Performance</th><th>Seat</th><th>Customer</th></tr>
        """.trimIndent()

    private fun reportFooter() =
        """
            </table>
        """.trimIndent()
}
