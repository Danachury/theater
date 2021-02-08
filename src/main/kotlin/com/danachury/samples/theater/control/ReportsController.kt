package com.danachury.samples.theater.control

import com.danachury.samples.theater.services.ReportsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView
import javax.websocket.server.PathParam
import kotlin.reflect.full.declaredMemberFunctions


@Controller
@RequestMapping("/reports")
class ReportsController {

    @Autowired
    lateinit var reportsService: ReportsService

    private fun getDeclaredFunctions() =
        reportsService::class.declaredMemberFunctions

    fun getListOfReports() =
        getDeclaredFunctions().map { it.name }

    @GetMapping
    fun main() =
        ModelAndView("Reports", mapOf("reports" to getListOfReports()))

    @GetMapping(value = ["/getReport"])
    fun getReport(@PathParam("report") report: String): ModelAndView {
        val method = getDeclaredFunctions().firstOrNull { it.name == report }
        val result = method?.call(reportsService) ?: ""
        return ModelAndView("Reports", mapOf("reports" to getListOfReports(), "result" to result))
    }
}
