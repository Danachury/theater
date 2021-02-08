package com.danachury.samples.theater.control

import com.danachury.samples.theater.data.PerformanceRepository
import com.danachury.samples.theater.data.SeatRepository
import com.danachury.samples.theater.domain.Booking
import com.danachury.samples.theater.domain.Performance
import com.danachury.samples.theater.domain.Seat
import com.danachury.samples.theater.services.BookingService
import com.danachury.samples.theater.services.TheaterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView

@Controller
class SeatBookingController {

    @Autowired
    lateinit var theaterService: TheaterService

    @Autowired
    lateinit var bookingService: BookingService

    @Autowired
    lateinit var seatRepository: SeatRepository

    @Autowired
    lateinit var performanceRepository: PerformanceRepository

    @GetMapping
    fun homePage(): ModelAndView {
        val model = mapOf(
            "bean" to CheckAvailabilityBookingBean(),
            "seatsRow" to 'A'..'O',
            "seatsNum" to 1..36,
            "performances" to performanceRepository.findAll()
        )
        return ModelAndView("SeatBooking", model)
    }

    @PostMapping(value = ["/checkAvailability"])
    fun checkAvailability(bean: CheckAvailabilityBookingBean): ModelAndView {
        val selectedSeat = bookingService.findSeat(bean.selectedSeatRow, bean.selectedSeatNum)!!
        val selectedPerformance = performanceRepository.findById(bean.selectedPerformance!!).get()
        val result = bookingService.isSeatFree(selectedSeat, selectedPerformance)
        bean.seat = selectedSeat
        bean.performance = selectedPerformance
        bean.available = result
        if (!result)
            bean.booking = bookingService.findBooking(selectedSeat, selectedPerformance)

        val model = mapOf(
            "bean" to bean,
            "seatsRow" to 'A'..'O',
            "seatsNum" to 1..36,
            "performances" to performanceRepository.findAll()
        )
        return ModelAndView("SeatBooking", model)
    }

    @PostMapping(value = ["/booking"])
    fun bookASeat(bean: CheckAvailabilityBookingBean): ModelAndView {
        val booking = bookingService.reserveSeat(bean.seat!!, bean.performance!!, bean.customerName)
        return ModelAndView("BookingConfirmed", "booking", booking)
    }

    @GetMapping(value = ["bootstrap"])
    fun createInitialData(): ModelAndView {
        seatRepository.saveAll(theaterService.seats)
        return homePage()
    }
}

class CheckAvailabilityBookingBean {

    var selectedSeatRow: Char = 'A'
    var selectedSeatNum: Int = 1
    var selectedPerformance: Long? = null
    var customerName: String = ""

    var available: Boolean? = null
    var seat: Seat? = null
    var performance: Performance? = null
    var booking: Booking? = null
}
