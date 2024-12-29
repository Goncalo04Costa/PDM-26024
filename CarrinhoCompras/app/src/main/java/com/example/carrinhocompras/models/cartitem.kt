package com.example.carrinhocompras.models

data class cartitem (
    val produto: product?=product("",0,"",""),
    var quantidade: Int?=1
        )