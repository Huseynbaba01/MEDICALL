package com.creativeprojects.medicall.DataHolder

class OrderOfEditText {
    var order: Int = 1

    fun getOrder() : Int?{
        return order
    }

    fun decreaseOrder(){
        order-=1
    }

    fun increaseOrder(){
        order+=1
    }
}