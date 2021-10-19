package com.server.dininghall.services

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.server.dininghall.Table
import com.server.dininghall.models.FoodItem
import com.server.dininghall.models.Order
import org.springframework.stereotype.Component
import java.util.*

@Component
class Menu() {

    private var menu: List<FoodItem> = getMeThatMenu()
    //id
    private var indexId: Int = 0

    fun generateOrder(table: Table): Order {
        val maxFoodItems: Int = (0..2).random()
        var preparationTime: Double = 0.0
        var orderList: MutableList<Int> = arrayListOf()
        val priority: Int = (1..3).random()

        for (i in 0..maxFoodItems) {
            var currentFoodInIteration = menu[(0..9).random()]
            preparationTime += currentFoodInIteration.`preparation-time`
            orderList.add(currentFoodInIteration.id)
        }
        indexId += 1;

        println("Table ${table.tableNumber} has order id  " + indexId + " with the items " + orderList + " that will be done in " + preparationTime*1.3)
        return Order(indexId, orderList, 2, (preparationTime * 1.3).toInt())
    }


    fun getMeThatMenu() : List<FoodItem> {

        val jsonParser = JsonParser()
        val jsonData: JsonObject = jsonParser.parse(""" 
                {
                    "Menu": [
                        {
                            "id": 1,
                            "name": "pizza",
                            "preparation-time": 20 ,
                            "complexity": 2 ,
                            "cooking-apparatus": "oven"
                        },
                        {
                            "id": 2,
                            "name": "salad",
                            "preparation-time": 10 ,
                            "complexity": 1 ,
                            "cooking-apparatus": null
                        },
                        {
                            "id": 3,
                            "name": "zeama",
                            "preparation-time": 7 ,
                            "complexity": 1 ,
                            "cooking-apparatus": "stove"
                        },
                        {
                            "id": 4,
                            "name": "Scallop Sashimi with Meyer Lemon Confit",
                            "preparation-time": 32 ,
                            "complexity": 3 ,
                            "cooking-apparatus": null
                        },
                        {
                            "id": 5,
                            "name": "Island Duck with Mulberry Mustard",
                            "preparation-time": 35 ,
                            "complexity": 3 ,
                            "cooking-apparatus": "oven"
                        },
                        {
                            "id": 6,
                            "name": "Waffles",
                            "preparation-time": 10 ,
                            "complexity": 1 ,
                            "cooking-apparatus": "stove"
                        },
                        {
                            "id": 7,
                            "name": "Aubergine",
                            "preparation-time": 20 ,
                            "complexity": 2 ,
                            "cooking-apparatus": null
                        },
                        {
                            "id": 8,
                            "name": "Lasagna",
                            "preparation-time": 30 ,
                            "complexity": 2 ,
                            "cooking-apparatus": "oven"
                        },
                        {
                            "id": 9,
                            "name": "Burger",
                            "preparation-time": 15 ,
                            "complexity": 1 ,
                            "cooking-apparatus": "oven"
                        },
                        {
                            "id": 10,
                            "name": "Gyros",
                            "preparation-time": 15 ,
                            "complexity": 1 ,
                            "cooking-apparatus": null
                        }
                    ]
                }
            """.trimIndent()) as JsonObject
        val jsonMenu = jsonData.getAsJsonArray("Menu")
        val gson: Gson = Gson()
        val returnMenu: MutableList<FoodItem> = arrayListOf()
        jsonMenu.forEach {
            returnMenu.add(gson.fromJson(it, FoodItem::class.java))
        }
        return Collections.unmodifiableList(returnMenu)
    }
}



