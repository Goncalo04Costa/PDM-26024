package com.example.goncalostore.app.domain.model

import android.util.Log

data class Cart (
    var idCarrinho:String?="",
    var donoCarrinho:String?="",
    var listaProdutos:MutableList<CartItem> = mutableListOf()
){
    fun AddProduct(prod:Product, quantity:Int){
        val productExists = listaProdutos.find { it.produto == prod }
        if(productExists!=null){
            productExists.quantidade = productExists.quantidade?.plus(quantity)
            Log.d("Carrinho","Quantidade updated:${productExists.quantidade}")
        }else{
            val newProdutoCarrinho = CartItem(prod, quantity)
            listaProdutos.add(newProdutoCarrinho)
            Log.d("Carrinho","Produto adicionado :${newProdutoCarrinho}")
        }
    }

    fun RemoveProduct(prod:Product){
        val productExists = listaProdutos.find { it.produto==prod }
        if(productExists!=null){
            listaProdutos.remove(productExists)
        }else{
            Log.d("Carrinho","O produto não existe")
        }
    }


}