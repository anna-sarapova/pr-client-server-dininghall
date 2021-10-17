package com.server.dininghall.models

class OrderRequest(val order_id: Int?, val table_id: Int, val waiter_id: Int, val items: MutableList<Int>?, val priority: Int?, val max_wait: Int? ) {
    fun toJSON(): String {
        return """
            {
                "order_id" : $order_id,
                "table_id" : $table_id,
                "waiter_id" : $waiter_id,
                "items" : $items,
                "priority" : $priority,
                "max_wait" : $max_wait,
                "pick_up_time" : ${System.currentTimeMillis()}
            }
        """.trimIndent()
    }
}