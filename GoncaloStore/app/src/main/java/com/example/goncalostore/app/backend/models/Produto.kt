package com.example.goncalostore.app.backend.models


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "produtos")
data class Produto(
    @PrimaryKey val id: Int = 0,
    @ColumnInfo(name = "nome") val nome: String = "",
    @ColumnInfo(name = "descricao") val descricao: String = "",
    @ColumnInfo(name = "preco") val preco: Int = 0
) {

    // Construtor para Firebase
    constructor(
        id: Int?,
        nome: String,
        descricao: String,
        preco: Int
    ) : this(id = id ?: 0, nome = nome, descricao = descricao, preco = preco)

    // Prepara dados para Firebase
    fun toStore(): Map<String, Any> {
        return mapOf(
            "nome" to this.nome,
            "descricao" to this.descricao,
            "preco" to this.preco
        )
    }
}


