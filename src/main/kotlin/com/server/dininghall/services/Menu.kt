package com.server.dininghall.services

import com.server.dininghall.models.Order
import org.springframework.stereotype.Component

@Component
class Menu {

    fun generateOrder(): Order {
        return Order(69, arrayListOf(3, 2, 5), 2, 30)
    }
}