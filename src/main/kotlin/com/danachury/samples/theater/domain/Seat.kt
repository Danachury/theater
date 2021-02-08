package com.danachury.samples.theater.domain

import javax.persistence.*

@Entity
data class Seat(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    val row: Char,
    val num: Int,
    val price: Double,
    val description: String
) {
    override fun toString() =
        "Seat $row-$num $$price ($description)"
}
