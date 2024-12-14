package com.example.carrinhodecompras.Classes

data class Produto (
    val nome: String?="",
    var preco: Int?=0,
    val descricao: String?="",
    val idUtilizador: String?=""
)
{
    constructor(nome: String, preco: Int):this (nome,preco,"","")

    fun updatePrice(product:Produto, price:Int){
        product.preco = price
    }
    fun isDefault():Boolean{
        return this == Produto("",0,"","")
    }
}