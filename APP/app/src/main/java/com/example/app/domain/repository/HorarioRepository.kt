package com.example.app.domain.repository

import com.example.app.domain.model.Horario
import java.time.LocalDate

class HorarioRepository {
    private val horarios = mutableListOf<Horario>()

    // Adicionar um horário
    fun addHorario(horario: Horario) {
        horarios.add(horario)
        println("Horário adicionado: $horario")
    }

    // Obter todos os horários
    fun getHorarios(): List<Horario> {
        return horarios
    }

    // Atualizar um horário por data (dia)
    fun updateHorario(dia: LocalDate, novoHorario: Horario): Boolean {
        val index = horarios.indexOfFirst { it.dia == dia }
        if (index != -1) {
            horarios[index] = novoHorario
            println("Horário atualizado: $novoHorario")
            return true
        }
        return false
    }

    // Remover um horário por data (dia)
    fun deleteHorario(dia: LocalDate): Boolean {
        return horarios.removeIf { it.dia == dia }
    }
}
