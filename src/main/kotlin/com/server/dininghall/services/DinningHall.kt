package com.server.dininghall.services

import com.server.dininghall.Table
import com.server.dininghall.Waiter
import org.springframework.stereotype.Service

// This acts as a service
@Service
class DinningHall {

    var tables: MutableList<Table> = arrayListOf()
    var waiters: MutableList<Waiter> = arrayListOf()
    val tableSize: Int = 9
    val waitersSize: Int = 1

    // Initialize x tables
    init {
        for(i in 0..tableSize) {
            tables.add(Table(i))
        }

        for(i in 0..waitersSize) {
            waiters.add(Waiter(i, tableSize, this))
            waiters[i].start()
        }
    }

    fun getTable(id: Int?) : Table {
        return if (id != null)
            tables[id]
        else {
            val randomIndex = (0..tableSize).random()
            tables[randomIndex]
        }
    }
}