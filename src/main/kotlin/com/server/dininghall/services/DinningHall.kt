package com.server.dininghall.services

import com.server.dininghall.Table
import com.server.dininghall.Waiter
import com.server.dininghall.enum.TableState
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

// This acts as a service
@Service
class DinningHall {

    private val logger = LoggerFactory.getLogger(DinningHall::class.java)

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


    @Scheduled(fixedRate = 5000)
    fun fixedRateScheduledTask() {
        val randomIndex = (0..tableSize).random()
        var table : Table = tables[randomIndex]

        if (table.tableState == TableState.FREE) {
            table.tableState = TableState.OCCUPIED
        }
        logger.info("Customers are Eating")
        if (table.tableState == TableState.EATING) {
            table.tableState = TableState.FREE
        }
    }
}