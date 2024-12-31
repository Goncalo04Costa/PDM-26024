package com.example.goncalostore.app.domain.model

data class CartItem(
    val produto: Product?=Product("",0,"",""),
    var quantidade: Int?=1
)