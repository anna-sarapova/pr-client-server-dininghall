package com.server.dininghall

import com.server.dininghall.enum.TableState
import com.server.dininghall.models.Order
import com.server.dininghall.models.OrderRequest
import com.server.dininghall.services.DinningHall
import com.server.dininghall.services.Menu
import org.slf4j.LoggerFactory
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class Waiter(id: Int, private val numberOfTables: Int, private val url: String?, private val dinningHall: DinningHall, private val menu: Menu): Thread() {

    private val logger = LoggerFactory.getLogger(Waiter::class.java)

    private val waiterId: Int = id



    private val lazyFactor: Int = 4

    override fun run() {
        logger.info("${Thread.currentThread()} has run. Waiter $waiterId at your service!")
        // logger.info("Waiter $waiterId I'm ready to take orders")
        val client = HttpClient.newBuilder().build();

        while(true) {
            Thread.sleep(((0..lazyFactor).random() * 1000).toLong())

            val awaitingOrder: MutableList<Int> = dinningHall.getWaitingOrderList()

            for (i in 0..numberOfTables) {
                if(!awaitingOrder.contains(i)) {
                    // logger.info("Waiter $waiterId checking table - $i")
                    val table: Table = dinningHall.getTable(i)
                    if (table.tableState == TableState.OCCUPIED) {
                        // simplifying logic to use WAITING_ORDER only, not TAKING_ORDER
                        logger.info("Table ${table.tableNumber} is occupied, Waiter $waiterId taking order")
                        // TODO add taking order sleep
                        table.tableState = TableState.WAITING_ORDER
                        dinningHall.addWaitingOrderList(table.tableNumber)

                        val data: Order? = table.tableOrder

                        val orderRequest: OrderRequest = OrderRequest(
                                data?.id, table.tableNumber, waiterId, data?.items, data?.priority, data?.max_wait
                        )

                        val request = HttpRequest.newBuilder()
                                .uri(URI.create("http://${url}:8080/order"))
                                .POST(HttpRequest.BodyPublishers.ofString(orderRequest.toJSON()))
                                .header("Content-Type", "application/json")
                                .build()

                        val response = client.send(request, HttpResponse.BodyHandlers.ofString());

                        logger.info(response.body())

                        Thread.sleep(((0..lazyFactor).random() * 1000).toLong())
                    }
                }
                // logger.info("Waiter $waiterId sees that table $i is currently ${table.tableState}")
            }

//            checkOrders(client)

        }
    }

    private fun checkOrders(client: HttpClient, awaitingOrder: MutableList<Int>) {
        awaitingOrder.forEach {
            val request = HttpRequest.newBuilder()
                    .uri(URI.create("http://${url}:8081/order/$it"))
                    .header("Content-Type", "application/json")
                    .build()

            val response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }
    }
}