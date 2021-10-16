package com.server.dininghall.controllers

import com.server.dininghall.Table
import com.server.dininghall.services.DinningHall
import com.server.dininghall.models.Distribution
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
class DiningHallController(val dinningHall : DinningHall) {

    private val logger = LoggerFactory.getLogger(DiningHallController::class.java)

    @GetMapping("/ping")
    fun ping(): String {
        logger.info("LOG info")
        return "Hello, I am the dining hall, We're ready to send orders!"
    }

    @PostMapping("/distribution")
    fun distribution(@RequestBody distribution: Distribution) : String {
        logger.info("Order created")
        return "Order with id: " + distribution.order_id + " has been created. To be processed..."
    }

    // returns a table, if id not specified returns a random table
    @GetMapping("/table")
    fun getTable(@RequestParam(required = false) id: Int?) : String {
        val table: Table = dinningHall.getTable(id)
        return table.toString() + " " + table.tableNumber.toString() + " " + table.tableState
    }
}