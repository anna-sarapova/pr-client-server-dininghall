package com.server.dininghall.services

import com.server.dininghall.Table
import com.server.dininghall.Waiter
import com.server.dininghall.enum.TableState
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

// This acts as a service
@Service
class DinningHall(val menu: Menu) {

    //TODO fix null url
    @Value("\${app.urls}")
    private val url: String? = "localhost"

    private val logger = LoggerFactory.getLogger(DinningHall::class.java)

    private var tables: MutableList<Table> = arrayListOf()
    private var waiters: MutableList<Waiter> = arrayListOf()
    private val tableSize: Int = 9
    private var waitingOrderList: MutableList<Int> = arrayListOf()
    private val waitersSize: Int = 0


    // Initialize x tables
    init {
        for(i in 0..tableSize) {
            tables.add(Table(i, null))
        }

        for(i in 0..waitersSize) {
            waiters.add(Waiter(i, tableSize, url,this, Menu()))
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

    fun addWaitingOrderList(id: Int) {
        if(!waitingOrderList.contains(id)) {
            waitingOrderList.add(id)
        }
    }

    fun removeWaitingOrderList(id: Int) {
        if(waitingOrderList.contains(id)) {
            waitingOrderList.remove(id)
        }
    }

    fun getWaitingOrderList(): MutableList<Int> {
        return waitingOrderList
    }

    @Scheduled(fixedRate = 5000)
    fun fixedRateScheduledTask() {
        val randomIndex = (0..tableSize).random()
        var table : Table = tables[randomIndex]

        when (table.tableState) {
            TableState.FREE -> {
                logger.info("Table ${table.tableNumber} was occupied")
                table.tableState = TableState.OCCUPIED
                table.tableOrder = menu.generateOrder(table);
            }
            TableState.EATING -> {
                logger.info("Table ${table.tableNumber} was freed")
                table.tableState = TableState.FREE
            }
            else -> {
                logger.info("Tables remain the same")
            }
        }
    }
}