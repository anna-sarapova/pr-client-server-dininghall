package com.server.dininghall

import com.server.dininghall.enum.TableState

class Table(number: Int) {
    val tableNumber: Int = number
    val tableState: TableState = TableState.AVAILABLE
}