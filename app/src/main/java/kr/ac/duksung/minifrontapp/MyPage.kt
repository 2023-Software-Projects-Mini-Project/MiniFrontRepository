package kr.ac.duksung.minifrontapp

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_my_page.*

class MyPage : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private lateinit var auth : FirebaseAuth
    private lateinit var username_area: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)

        // '카드 관리' 항목 클릭 이벤트 처리
        mp_card.setOnClickListener {
            val intent = Intent(this@MyPage, Card::class.java)
            startActivity(intent)
        }

        // '리뷰 관리' 항목 클릭 이벤트 처리
        mp_review.setOnClickListener {
            val intent = Intent(this@MyPage, MyReview::class.java)
            startActivity(intent)
        }

        // '주문 내역' 항목 클릭 이벤트 처리
        mp_order_detail.setOnClickListener {
            val intent = Intent(this@MyPage, OrderDetails::class.java)
            startActivity(intent)
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        // 바텀 네비게이션 아이템 클릭 리스너 설정
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.page_home -> {
                    // 홈 아이템 클릭 시 홈 화면으로 이동
                    startActivity(Intent(this@MyPage, HomeFragment::class.java))
                    true
                }
                R.id.page_fv -> {
                    // 친구 추가 아이템 클릭 시 친구 추가 화면으로 이동
                    startActivity(Intent(this@MyPage, FriendsList::class.java))
                    true
                }
                else -> false
            }
        }

        auth = FirebaseAuth.getInstance()
        username_area = findViewById(R.id.username_area)

        val docRef  =  db.collection("users").document(auth.currentUser?.uid.toString())
        docRef.get().addOnSuccessListener { documentSnapshot ->
            username_area.setText(documentSnapshot.get("username").toString())
        }
    }
}