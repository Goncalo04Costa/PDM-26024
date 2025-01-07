package com.example.goncalostore.app.backend.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "carrinhos")
data class Carrinho(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "nome")
    val nome: String = "",

    @ColumnInfo(name = "criadoPor")
    val criadoPor: String = "",

    @ColumnInfo(name = "produtos")
    val produtos: List<Produtocarrinho> = emptyList(),

    @ColumnInfo(name = "compartilhadoCom")
    val compartilhadoCom: List<String> = emptyList()
) {

    // Prepara dados para Firebase
    fun toStore(): Map<String, Any> {
        return mapOf(
            "id" to this.id,
            "nome" to this.nome,
            "criadoPor" to this.criadoPor,
            "produtos" to this.produtos.map { it.toStore() },
            "compartilhadoCom" to this.compartilhadoCom
        )
    }
}