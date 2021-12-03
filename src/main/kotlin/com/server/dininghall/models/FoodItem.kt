package com.server.dininghall.models

data class FoodItem(
        val id: Int,
        val name: String,
        val `preparation-time`: Int,
        val complexity: Int,
        val `cooking-apparatus`: String) {
}