package com.server.dininghall

import com.server.dininghall.enum.TableState
import com.server.dininghall.models.Order

class Table(number: Int, order: Order?) {
    val tableNumber: Int = number
    var tableOrder: Order? = order
    var tableState: TableState = TableState.FREE
}