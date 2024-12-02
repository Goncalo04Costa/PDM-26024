package com.example.loja.classes

class Utilizador(
    val id: Int,
    val nome: String,
    val email: String,
    private val pass: String,
    val carrinho: Carrinho = Carrinho()
) {
    fun verificarSenha(senhaInput: String): Boolean {
        return pass == senhaInput
    }
}
