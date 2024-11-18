package com.example.app.domain.repository

import com.example.app.domain.model.Utilizador

class UtilizadorRepository {
    private val utilizadores = mutableListOf<Utilizador>()

    // Adicionar um utilizador
    fun addUtilizador(utilizador: Utilizador) {
        utilizadores.add(utilizador)
        println("Utilizador adicionado: $utilizador")
    }

    // Obter todos os utilizadores
    fun getUtilizadores(): List<Utilizador> {
        return utilizadores
    }

    // Atualizar um utilizador por ID
    fun updateUtilizador(id: String, novoUtilizador: Utilizador): Boolean {
        val index = utilizadores.indexOfFirst { it.id == id }
        if (index != -1) {
            utilizadores[index] = novoUtilizador
            println("Utilizador atualizado: $novoUtilizador")
            return true
        }
        return false
    }

    // Remover um utilizador por ID
    fun deleteUtilizador(id: String): Boolean {
        return utilizadores.removeIf { it.id == id }
    }


}
