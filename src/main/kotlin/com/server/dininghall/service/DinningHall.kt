package com.server.dininghall.service

import com.server.dininghall.Table
import org.springframework.stereotype.Service

// This acts as a service
@Service
class DinningHall {

    var tables: MutableList<Table> = arrayListOf()
    val tableSize: Int = 9

    // Initialize x tables
    init {
        for(i in 0..tableSize) {
            tables.add(Table(i))
        }
    }

    fun getTable(id: Int?) : String {
        return if (id != null)
            tables[id].toString() + " " + tables[id].tableNumber.toString() + " " + tables[id].tableState
        else {
            val randomIndex = (0..tableSize).random()
            tables[randomIndex].toString() + " " + tables[randomIndex].tableNumber.toString() + " " + tables[randomIndex].tableState
        }
    }
}