package com.server.dininghall.models

class Order(val id: Int, val items: MutableList<Int>, val priority: Int, val max_wait: Int) {
    fun toJSON(): String {
        return """
            {
                "id" : $id,
                "items" : $items,
                "priority" : $priority,
                "max_wait" : $max_wait
            }
        """.trimIndent()
    }
}