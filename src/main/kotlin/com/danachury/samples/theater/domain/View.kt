package com.danachury.samples.theater.domain

enum class View(val price: Double, val description: String) {
    BACK_ROW(14.50, "Back Row"),
    CHEAPER_SEAT(14.50, "Cheaper Seat"),
    RESTRICTED_VIEW(16.50, "Restricted View"),
    STANDARD_VIEW(18.00, "Standard View"),
    BEST_VIEW(21.00, "Best View");
}
