package kr.ac.duksung.minifrontapp

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FoodProvider(private val reference: CollectionReference? = null) {
    var food: List<FoodList.Food> = mutableListOf()
        private set

    private lateinit var foodReference: CollectionReference

    init {
        foodReference = reference ?: FirebaseFirestore.getInstance().collection("food")
    }

    suspend fun fetchFoods() {
        try {
            val results = foodReference.get().await()
            food = results.documents.mapNotNull { document ->
                FoodList.Food.fromSnapshot(document)
            }
        } catch (e: Exception) {
            // 처리할 예외 처리 코드
        }
    }
}
