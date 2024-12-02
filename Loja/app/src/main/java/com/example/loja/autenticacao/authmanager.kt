package com.example.loja.auth

import com.example.loja.classes.Utilizador

class AuthManager {
    private val utilizadores = mutableListOf<Utilizador>()
    private var utilizadorAutenticado: Utilizador? = null

    // Registrar um novo utilizador
    fun registrarUtilizador(utilizador: Utilizador) {
        if (utilizadores.any { it.email == utilizador.email }) {
            println("Erro: Já existe um utilizador com este email.")
        } else {
            utilizadores.add(utilizador)
            println("Utilizador '${utilizador.nome}' registrado com sucesso.")
        }
    }

    // Login
    fun login(email: String, senha: String): Boolean {
        val utilizador = utilizadores.find { it.email == email }
        return if (utilizador != null && utilizador.verificarSenha(senha)) {
            utilizadorAutenticado = utilizador
            println("Login bem-sucedido! Bem-vindo, ${utilizador.nome}.")
            true
        } else {
            println("Erro: Email ou senha incorretos.")
            false
        }
    }

    // Logout
    fun logout() {
        if (utilizadorAutenticado != null) {
            println("Logout realizado com sucesso. Adeus, ${utilizadorAutenticado?.nome}.")
            utilizadorAutenticado = null
        } else {
            println("Nenhum utilizador autenticado no momento.")
        }
    }

    // Verificar utilizador autenticado
    fun obterUtilizadorAutenticado(): Utilizador? {
        return utilizadorAutenticado
    }
}
