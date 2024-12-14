package com.example.carrinhodecompras.Classes

import android.util.Log

data class Carrinho (
    var idCarrinho:String?="",
    var donoCarrinho:String?="",
    var listaProdutos:MutableList<ProdutoCarrinho> = mutableListOf()
){
    fun AddProduct(prod:Produto, quantity:Int){
        val productExists = listaProdutos.find { it.produto == prod }
        if(productExists!=null){
            productExists.quantidade = productExists.quantidade?.plus(quantity)
            Log.d("Carrinho","Quantidade updated:${productExists.quantidade}")
        }else{
            val newProdutoCarrinho = ProdutoCarrinho(prod, quantity)
            listaProdutos.add(newProdutoCarrinho)
            Log.d("Carrinho","Produto adicionado :${newProdutoCarrinho}")
        }
    }

    fun RemoveProduct(prod:Produto){
        val productExists = listaProdutos.find { it.produto==prod }
        if(productExists!=null){
            listaProdutos.remove(productExists)
        }else{
            Log.d("Carrinho","O produto não existe")
        }
    }


}
