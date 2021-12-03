package com.server.dininghall

import com.server.dininghall.enum.TableState
import com.server.dininghall.models.Distribution
import com.server.dininghall.models.OrderRequest
import com.server.dininghall.services.DinningHall
import org.slf4j.LoggerFactory
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class Waiter(id: Int, private val numberOfTables: Int, private val url: String?,
             private val dinningHall: DinningHall): Thread() {

    private val logger = LoggerFactory.getLogger(Waiter::class.java)

    private val waiterId: Int = id

    private val lazyFactor: Int = 8

    override fun run() {
        logger.info("${Thread.currentThread()} has run. Waiter $waiterId at your service!")
        logger.info("Waiter $waiterId I'm ready to take orders")

        while(true) {
            Thread.sleep(((0..lazyFactor).random() * 1000).toLong())

            val awaitingOrder: MutableList<Int> = dinningHall.getWaitingOrderList()

            checkTables(awaitingOrder) // PASSES BY EVERY TABLE AND TAKES ORDER AND SENDS TO KITCHEN

            val doneOrder: Distribution? = dinningHall.pickOrder(waiterId)

            if (doneOrder != null) {
                var finalTime = (System.currentTimeMillis() - doneOrder.pick_up_time ) / 1000
                dinningHall.serveTable(doneOrder.table_id)
                dinningHall.removeWaitingOrderList(doneOrder.table_id)
                dinningHall.removeDoneOrders(doneOrder)
                logger.info("Serving table ${doneOrder.table_id} with the order ${doneOrder.order_id}. " +
                        "taken order time: ${doneOrder.pick_up_time}. Took kitchen ${doneOrder.cooking_time} units of time to make it. " +
                        "Order at table took: ${finalTime}s" )
                print("Done order list: " + doneOrder)

                var result: Int
                if(finalTime < doneOrder.max_wait) {
                    result = 5
                } else if (finalTime > doneOrder.max_wait && finalTime < doneOrder.max_wait * 1.1) {
                    result = 4
                } else if (finalTime > doneOrder.max_wait * 1.1 && finalTime < doneOrder.max_wait * 1.2){
                    result = 3
                } else if (finalTime > doneOrder.max_wait * 1.2 && finalTime < doneOrder.max_wait * 1.3) {
                    result = 2
                } else if (finalTime > doneOrder.max_wait * 1.3 && finalTime < doneOrder.max_wait * 1.4) {
                    result = 1
                } else result = 0
                println(" You receive ${result} stars")
            }
        }
    }

    private fun checkTables(awaitingOrder: MutableList<Int>) {
        lateinit var orderRequest: OrderRequest
        for (i in 0..numberOfTables) {
            // optimization reasons by me :)
            if (!awaitingOrder.contains(i)) {
                logger.info("Waiter $waiterId checking table - $i")
                val table: Table = dinningHall.getTable(i)
                if (table.tableState == TableState.OCCUPIED) {
                    orderRequest = dinningHall.takeOrder(table, waiterId)

                    val response = sendOrder(orderRequest)

                    logger.info(response?.body())

                    sleep(((0..lazyFactor).random() * 1000).toLong())
                }
                logger.info("Waiter $waiterId sees that table $i is currently ${table.tableState}")
            }
        }
    }

    private fun sendOrder(orderRequest: OrderRequest): HttpResponse<String>? {
        val client = HttpClient.newBuilder().build()
        val request = HttpRequest.newBuilder()
                .uri(URI.create("http://${url}:8080/order"))
                .POST(HttpRequest.BodyPublishers.ofString(orderRequest.toJSON()))
                .header("Content-Type", "application/json")
                .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        return response
    }
}