package com.example.gitflow.data.repository

import com.example.gitflow.data.remote.ApiService
import com.example.gitflow.domain.Category
import com.example.gitflow.domain.Furniture
import com.example.gitflow.domain.Model
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FurnitureRepository(private val apiService: ApiService) {
    private val firestore = FirebaseFirestore.getInstance()
    private val collectionRef = firestore.collection("categories")  // Firestore collection for categories

    suspend fun getFurnitureData(): Model {
        return try {
            val response = apiService.getFurniture()
            println("API Response: $response")
            storeDataInFirestore(response.categories)

            response
        } catch (e: Exception) {
            println("API Error: ${e.message}")
            fetchFromFirestore() ?: Model(emptyList())
        }
    }

    private fun storeDataInFirestore(categories: List<Category>) {
        categories.forEach { category ->
            val categoryDocRef = collectionRef.document(category.name)

            // Store the category itself
            categoryDocRef.set(mapOf("name" to category.name))
                .addOnSuccessListener { println("Stored Category: ${category.name}") }
                .addOnFailureListener { e -> println("Firestore Error (Category): ${e.message}") }

            // Store each furniture item in the "furnitures" subcollection
            category.furnitures.forEach { furniture ->
                categoryDocRef.collection("furnitures").document(furniture.title)  // Using title as document ID
                    .set(furniture)
                    .addOnSuccessListener { println("Stored Furniture: ${furniture.title}") }
                    .addOnFailureListener { e -> println("Firestore Error (Furniture): ${e.message}") }
            }
        }
    }


    private suspend fun fetchFromFirestore(): Model? {
        return try {
            val snapshot = collectionRef.get().await()
            val categories = snapshot.documents.mapNotNull { categoryDoc ->
                val name = categoryDoc.getString("name") ?: return@mapNotNull null

                // Fetch furnitures for this category
                val furnituresSnapshot = categoryDoc.reference.collection("furnitures").get().await()
                val furnitures = furnituresSnapshot.documents.mapNotNull { it.toObject(Furniture::class.java) }

                Category(furnitures, name)
            }

            Model(categories)
        } catch (e: Exception) {
            println("Firestore Fetch Error: ${e.message}")
            null
        }
    }
}



