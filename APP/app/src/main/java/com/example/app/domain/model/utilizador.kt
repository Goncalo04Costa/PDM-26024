package com.example.app.domain.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

data class Utilizador (
    val id: String, // ID do utilizador (pode ser o UID do Firebase Authentication)
    val nome: String,
    val contacto: String, // Melhor usar String para formatos diversos
    val NIF: String, // Pode ser String para evitar problemas com zeros à esquerda
    val FotoPerfil: String, // URL da imagem armazenada no Firebase Storage
    val PassWord: String? = null, // Pode ser null, já que o Firebase Authentication pode gerenciar isso
    val horarios: MutableList<Horario> = mutableListOf()
) {
    // Método na classe Utilizador
    @RequiresApi(Build.VERSION_CODES.O)
    fun indicarDisponibilidade(dia: LocalDate) {
        if (dia.isBefore(LocalDate.now())) {
            println("Não é possível indicar disponibilidade para um dia no passado.")
            return
        }
        if (horarios.any { it.dia == dia }) {
            println("$nome já está disponível no dia $dia.")
            return
        }
        val horario = Horario(dia)  // Cria o objeto Horario
        horarios.add(horario)       // Adiciona à lista de horários do utilizador
        println("$nome agora está disponível no dia: $dia")
    }

}
