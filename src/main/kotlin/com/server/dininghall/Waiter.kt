package com.server.dininghall

import com.server.dininghall.services.DinningHall
import org.slf4j.LoggerFactory

class Waiter(id: Int, private val numberOfTables: Int, private val dinningHall: DinningHall): Thread() {

    private val logger = LoggerFactory.getLogger(Waiter::class.java)

    private val waiterId: Int = id

    private val lazyFactor: Int = 3

    override fun run() {
        logger.info("${Thread.currentThread()} has run. Waiter $waiterId at your service!")
        Thread.sleep(((0..lazyFactor).random() * 1000).toLong())
        logger.info("Waiter $waiterId I'm ready to take orders")
        for (i in 0..numberOfTables) {
            logger.info("Waiter $waiterId checking table - $i")
            val table: Table = dinningHall.getTable(i)
            Thread.sleep(((0..lazyFactor).random() * 1000).toLong())
            logger.info("Waiter $waiterId sees that table $i is currently ${table.tableState}")
        }
    }
}