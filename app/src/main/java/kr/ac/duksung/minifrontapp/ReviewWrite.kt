package kr.ac.duksung.minifrontapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

import android.widget.EditText
import android.widget.ImageView
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.review_write.*

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

        // EditText에서 리뷰 내용을 가져옴
       // val userReviewText = findViewById<EditText>(R.id.edit_review).text.toString()

        if (user != null) {
            val uid = user.uid

            // 사용자 UID를 사용하여 리뷰를 저장
            val reviewsRef = realdb.getReference("MenuName").child("김밥").child("reviews")
            //val userReviewText = "uid 넣지 말라네여"
            btn_finish.setOnClickListener {
                val userReviewText = edit_review.text.toString()

                // 사용자가 입력한 리뷰 텍스트를 가져와서 Firebase에 저장
                reviewsRef.push().setValue(userReviewText)

                // 리뷰를 저장한 후 EditText를 초기화하려면 아래와 같이 설정할 수 있습니다.
              //  findViewById<EditText>(R.id.edit_review).text.clear()
            }
        }

    }
}