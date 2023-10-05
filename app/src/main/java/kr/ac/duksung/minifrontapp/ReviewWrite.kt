package kr.ac.duksung.minifrontapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

import android.widget.EditText
import android.widget.ImageView
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore

class ReviewWrite : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.review_write)


        val items = arrayOf("김치찌개", "된장찌개", "부대찌개", "짜장면", "짬뽕", "탕수육", "김밥", "라면", "떡볶이")
        val database = FirebaseDatabase.getInstance()
        val realdb = Firebase.database("https://testlogin2-a82d6-default-rtdb.firebaseio.com/")
        val reviewsRef = realdb.getReference("MenuName").child("김밥").child("reviews")


// 사용자의 UID 가져오기 (Firebase Authentication에서 로그인한 경우)

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val uid = user.uid

            // 사용자 UID를 사용하여 리뷰를 저장
            val userReview = findViewById<EditText>(R.id.edit_review) // 사용자로부터 입력받은 리뷰 데이터
            reviewsRef.child(uid).push().setValue(userReview)
        }
    }
}