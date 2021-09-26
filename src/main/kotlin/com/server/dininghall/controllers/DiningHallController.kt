package com.server.dininghall.controllers

import com.server.dininghall.models.Distribution
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class DiningHallController {

    @GetMapping("/ping")
    fun ping(): String {
        return "Hello, I am the dining hall, We're ready to send orders!"
    }

    @PostMapping("/distribution")
    fun order(@RequestBody distribution: Distribution) : String {
        return "Order with id: " + distribution.order_id + " has been created. To be processed..."
    }
}