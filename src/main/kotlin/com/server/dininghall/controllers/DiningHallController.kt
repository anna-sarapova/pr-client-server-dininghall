package com.server.dininghall.controllers

import com.server.dininghall.models.Distribution
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class DiningHallController {

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
}