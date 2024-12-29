package com.example.carrinhodecompras.Classes

import com.example.carrinhodecompras.classes.Product

class ProdutoCarrinho (
    val produto: Product?=Product("","","",""),
    var quantidade: Int?=1
)