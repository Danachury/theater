package com.danachury.samples.theater.services

import com.danachury.samples.theater.domain.Seat
import com.danachury.samples.theater.domain.View
import org.springframework.stereotype.Service

@Service
class TheaterService {

    val seats: List<Seat> = (1..totalRows)
        .flatMap { row ->
            (1..totalSeatsByRow)
                .map { Seat(0, (row + 64).toChar(), it, getPrice(row, it), getDescription(row, it)) }
        }

    fun find(row: Char, num: Int) =
        seats.first { it.row == row && it.num == num }

    companion object {
        private const val totalRows = 15
        private const val totalSeatsByRow = 36

        private fun getPrice(row: Int, seatNum: Int) =
            when {
                row >= 14 -> View.BACK_ROW.price
                seatNum <= 3 || seatNum >= 34 -> View.RESTRICTED_VIEW.price
                row == 1 -> View.BEST_VIEW.price
                else -> View.STANDARD_VIEW.price
            }

        private fun getDescription(row: Int, seatNum: Int) =
            when {
                row == 15 -> View.BACK_ROW.description
                row == 14 -> View.CHEAPER_SEAT.description
                seatNum <= 3 || seatNum >= 34 -> View.RESTRICTED_VIEW.description
                row == 1 -> View.BEST_VIEW.description
                else -> View.STANDARD_VIEW.description
            }
    }
}
