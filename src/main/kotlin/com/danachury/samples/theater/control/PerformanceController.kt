package com.danachury.samples.theater.control

import com.danachury.samples.theater.data.PerformanceRepository
import com.danachury.samples.theater.domain.Performance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/performances")
class PerformanceController {

    @Autowired
    lateinit var performanceRepository: PerformanceRepository

    @GetMapping
    fun performancesHomePage() =
        ModelAndView("performances/Home", "performances", performanceRepository.findAll())

    @GetMapping(value = ["/add"])
    fun addPerformance() =
        ModelAndView("performances/Add", "performance", Performance(0, ""))

    @PostMapping(value = ["save"])
    fun savePerformance(performance: Performance): String {
        performanceRepository.save(performance)
        return "redirect:/performances/"
    }
}
