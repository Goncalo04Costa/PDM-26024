package com.example.goncalostore.app.domain.model

data class Product (
    val nome: String?="",
    var preco: Int?=0,
    val descricao: String?="",
    val idUtilizador: String?=""
)
{
    constructor(nome: String, preco: Int):this (nome,preco,"","")

    fun updatePrice(product:Product, price:Int){
        product.preco = price
    }
    fun isDefault():Boolean{
        return this == Product("",0,"","")
    }
}