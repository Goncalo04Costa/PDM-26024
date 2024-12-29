package com.example.carrinhocompras.models

import android.util.Log

data class cart(
    var idCarrinho:String?="",
    var donoCarrinho:String?="",
    var listaProdutos:MutableList<cartitem> = mutableListOf()
)
{
    fun AddProduct(prod:product, quantity:Int){
        val productExists = listaProdutos.find { it.produto == prod }
        if(productExists!=null){
            productExists.quantidade = productExists.quantidade?.plus(quantity)
            Log.d("Carrinho","Quantidade updated:${productExists.quantidade}")
        }else{
            val newProdutoCarrinho = cartitem(prod, quantity)
            listaProdutos.add(newProdutoCarrinho)
            Log.d("Carrinho","Produto adicionado :${newProdutoCarrinho}")
        }
    }

    fun RemoveProduct(prod:product){
        val productExists = listaProdutos.find { it.produto==prod }
        if(productExists!=null){
            listaProdutos.remove(productExists)
        }else{
            Log.d("Carrinho","O produto não existe")
        }
    }


}