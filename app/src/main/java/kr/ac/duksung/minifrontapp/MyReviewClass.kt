package kr.ac.duksung.minifrontapp

import com.google.firebase.database.Exclude
import java.util.Date

data class MyReviewClass(
    var menu: String,
    var rating: Float,
    var date: String,
    var contents: String
    )


