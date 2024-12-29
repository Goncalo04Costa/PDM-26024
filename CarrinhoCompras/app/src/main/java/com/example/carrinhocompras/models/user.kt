package com.example.carrinhocompras.models

data class user(
    val email: String,
    val password: String,
    val carrinhoCompras: List<Int>,
    var uid:String,
    val produtos: List<Int>
){
    constructor(email: String, password: String) : this(email, password, emptyList(),"", emptyList())
}