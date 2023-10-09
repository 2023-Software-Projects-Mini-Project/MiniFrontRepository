package kr.ac.duksung.minifrontapp

import com.google.firebase.firestore.DocumentSnapshot

class FoodList {

    data class Food(
        val id: String,
        val category: String,
        val foodname: String,
        val imageUrl: String,
        val price: Int
    ) {
        companion object {
            fun fromSnapshot(snapshot: DocumentSnapshot): Food {
                val data = snapshot.data ?: emptyMap()
                return Food(
                    id = snapshot.id,
                    category = data["category"] as? String ?: "",
                    foodname = data["foodname"] as? String ?: "",
                    imageUrl = data["imageUrl"] as? String ?: "",
                    price = (data["price"] as? Long)?.toInt() ?: 0
                )
            }
        }
    }
}
