package com.example.goncalostore.app.backend.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "produtocarrinho")
data class Produtocarrinho(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    @ColumnInfo(name = "carrinhoid")
    val carrinhoId: String = "",
    @ColumnInfo(name = "produto")
    val produto: Produto? = Produto(0, "", "", 0),

    @ColumnInfo(name = "quantidade")
    var quantidade: Int = 1
) {


    // Construtor para Firebase
    constructor(
        id: Int?,
        carrinhoId: String,
        produto: Produto?,
        quantidade: Int
    ) : this(id = id ?: 0, carrinhoId = carrinhoId, produto = produto, quantidade = quantidade)

    // Prepara dados para Firebase
    fun toStore(): Map<String, Any?> {
        return mapOf(
            "carrinhoId" to this.carrinhoId,
            "produto" to this.produto?.toStore(),
            "quantidade" to this.quantidade
        )
    }
}
