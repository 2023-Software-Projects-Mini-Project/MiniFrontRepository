package kr.ac.duksung.minifrontapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

import android.widget.EditText
import android.widget.ImageView
import com.google.firebase.database.ktx.database


class ReviewWrite : AppCompatActivity() {

    val realdb = Firebase.database("https://testlogin2-a82d6-default-rtdb.firebaseio.com/")
    val reviewsRef = realdb.getReference("MenuName").child("김밥").child("reviews")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.review_write)

        val backIcon = findViewById<ImageView>(R.id.back_icon)

        backIcon.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish() // 현재 액티비티 종료
            }
        })


// 사용자의 UID 가져오기 (Firebase Authentication에서 로그인한 경우)
        /*
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val uid = user.uid

            // 사용자 UID를 사용하여 리뷰를 저장
            val userReview = findViewById<EditText>(R.id.edit_review) // 사용자로부터 입력받은 리뷰 데이터
            reviewsRef.push().setValue(userReview)
        }
         */
    }
}