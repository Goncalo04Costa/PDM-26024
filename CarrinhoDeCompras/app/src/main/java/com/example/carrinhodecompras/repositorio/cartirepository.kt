package com.example.carrinhodecompras.repositorio

import com.example.carrinhodecompras.classes.Cart
import com.example.carrinhodecompras.classes.CartItem
import com.example.carrinhodecompras.classes.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class CartRepository {
    private val db = FirebaseFirestore.getInstance()
    private val cartCollection = db.collection("carts")

    suspend fun getCart(userId: String): Cart? {
        return try {
            val document = cartCollection.document(userId).get().await()
            if (document.exists()) {
                val items = (document["items"] as List<Map<String, Any>>).map { item ->
                    val productMap = item["product"] as Map<String, Any>
                    CartItem(
                        product = Product(
                            id = productMap["id"] as String,
                            name = productMap["name"] as String,
                            price = (productMap["price"] as Double),
                            description = productMap["description"] as String
                        ),
                        quantity = (item["quantity"] as Long).toInt()
                    )
                }
                Cart(userId = userId, items = items)
            } else null
        } catch (e: Exception) {
            null
        }
    }

    suspend fun addToCart(userId: String, product: Product, quantity: Int) {
        try {
            val currentCart = getCart(userId) ?: Cart(userId, emptyList())
            val updatedItems = currentCart.items.toMutableList().apply {
                val existingItem = find { it.product.id == product.id }
                if (existingItem != null) {
                    remove(existingItem)
                    add(existingItem.copy(quantity = existingItem.quantity + quantity))
                } else {
                    add(CartItem(product, quantity))
                }
            }
            cartCollection.document(userId).set(mapOf("items" to updatedItems)).await()
        } catch (e: Exception) {
            // Handle exceptions (e.g., log errors)
        }
    }

    suspend fun clearCart(userId: String) {
        try {
            cartCollection.document(userId).delete().await()
        } catch (e: Exception) {
            // Handle exceptions
        }
    }
}
