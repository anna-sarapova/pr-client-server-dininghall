package com.server.dininghall.services

import com.server.dininghall.Table
import com.server.dininghall.Waiter
import com.server.dininghall.enum.TableState
import com.server.dininghall.models.Distribution
import com.server.dininghall.models.Order
import com.server.dininghall.models.OrderRequest
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
    private var waitingOrders: MutableList<Int> = arrayListOf()
    private var doneOrders: MutableList<Distribution> = arrayListOf()
    private val waitersSize: Int = 3

    // Initialize x tables
    init {
        for (i in 0..tableSize) {
            tables.add(Table(i, null))
        }

        for (i in 0..waitersSize) {
            waiters.add(Waiter(i, tableSize, url, this))
            waiters[i].start()
        }
    }

    @Synchronized
    fun serveTable(tableId: Int) {
        tables[tableId].tableState = TableState.EATING
    }

    @Synchronized
    fun removeDoneOrders(doneOrder:Distribution){
        doneOrders.remove(doneOrder)
    }

    @Synchronized
    fun getTable(id: Int?): Table {
        return if (id != null)
            tables[id]
        else {
            val randomIndex = (0..tableSize).random()
            tables[randomIndex]
        }
    }

     @Synchronized
    fun addDoneOrder(distribution: Distribution) {
        doneOrders.add(distribution)
    }

    @Synchronized
    fun addWaitingOrderList(id: Int) {
        if (!waitingOrders.contains(id)) {
            waitingOrders.add(id)
        }
    }

    @Synchronized
    fun removeWaitingOrderList(id: Int) {
        if (waitingOrders.contains(id)) {
            waitingOrders.remove(id)
        }
    }

    @Synchronized
    fun getWaitingOrderList(): MutableList<Int> {
        return waitingOrders
    }

    @Synchronized
    fun pickOrder(id: Int): Distribution? {
        return doneOrders.find { it.waiter_id == id }
    }

    @Synchronized
    fun takeOrder(table: Table, waiterId: Int) : OrderRequest {
        // simplifying logic to use WAITING_ORDER only, not TAKING_ORDER
        logger.info("Table " + "" +
                " is occupied, Waiter $waiterId taking order")
        Thread.sleep(((2..4).random() * 1000).toLong())
        table.tableState = TableState.WAITING_ORDER
        addWaitingOrderList(table.tableNumber)

        val data: Order? = table.tableOrder

        val orderRequest: OrderRequest = OrderRequest(
                data?.id, table.tableNumber, waiterId, data?.items, data?.priority, data?.max_wait
        )
        return orderRequest
    }

    @Scheduled(fixedRate = 5000)
    fun fixedRateScheduledTask() {
        val randomIndex = (0..tableSize).random()
        var table: Table = tables[randomIndex]

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