package com.example.goncalostore.app.backend.models



import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "nome") val nome: String = "",
    @ColumnInfo(name = "email") val email: String = "",
    @ColumnInfo(name = "password") val password: String = "",
    @ColumnInfo(name = "Carrinhos") val carrinhos: List<String> = listOf(),
) {


    constructor(
        nome: String,
        email: String,
        password: String,
        carrinhos: List<String>,
        tipouser: String,
        id: Int?
    ) : this(id ?: 0,nome, email, password, carrinhos)
    // prepara dados para firebase
    fun toStore(): Map<String, Any> {
        return mapOf(
            "nome" to this.nome,
            "email" to this.email,
            "Carrinhos" to this.carrinhos
        )
    }
}
