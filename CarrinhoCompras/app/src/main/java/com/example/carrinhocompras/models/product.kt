package com.example.carrinhocompras.models

data class  product(
    val nome: String?="",
    var preco: Int?=0,
    val descricao: String?="",
    val userid: String?=""
)

{
    constructor(nome: String, preco: Int):this (nome,preco,"","")

    fun updatePrice(product:product, price:Int){
        product.preco = price
    }
    fun isDefault():Boolean{
        return this == product("",0,"","")
    }
}