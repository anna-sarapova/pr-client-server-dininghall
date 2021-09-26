package com.server.dininghall.controllers

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

//    @PostMapping("/order")
//    fun order(@RequestBody order: Order) : String {
//        logger.info("Order received")
//        return "Order with id: " + order.order_id + " has been received. To be processed..."
//    }
}