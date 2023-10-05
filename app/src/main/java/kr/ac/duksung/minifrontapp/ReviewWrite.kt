package kr.ac.duksung.minifrontapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

import android.widget.EditText
import android.widget.ImageView
import com.google.firebase.database.ktx.database
import kotlinx.android.synthetic.main.activity_card.*


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

        // 바텀 네비게이션 아이템 클릭 리스너 설정
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.page_home -> {
                    // 홈 아이템 클릭 시 홈 화면으로 이동
                    startActivity(Intent(this@ReviewWrite, HomeFragment::class.java))
                    true
                }
                R.id.page_fv -> {
                    // 친구 추가 아이템 클릭 시 친구 추가 화면으로 이동
                    startActivity(Intent(this@ReviewWrite, FriendsList::class.java))
                    true
                }
                R.id.page_ps -> {
                    // 마이페이지 아이템 클릭 시 마이페이지 화면으로 이동
                    startActivity(Intent(this@ReviewWrite, MyPage::class.java))
                    true
                }
                else -> false
            }
        }


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