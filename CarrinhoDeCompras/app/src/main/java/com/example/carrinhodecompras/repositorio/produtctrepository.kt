package com.example.carrinhodecompras.repositorio

import com.example.carrinhodecompras.classes.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ProductRepository {
    private val db = FirebaseFirestore.getInstance()
    private val productsCollection = db.collection("products")

    suspend fun getProducts(): List<Product> {
        return try {
            val snapshot = productsCollection.get().await()
            snapshot.documents.map { doc ->
                Product(
                    id = doc.id,
                    name = doc.getString("name") ?: "",
                    price = doc.getDouble("price") ?: 0.0,
                    description = doc.getString("description") ?: ""
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
