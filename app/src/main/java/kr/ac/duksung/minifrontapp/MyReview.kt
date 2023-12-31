package kr.ac.duksung.minifrontapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_card.bottomNavigationView
import kotlinx.android.synthetic.main.menu_review.*
import java.util.Date

class MyReview : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val db = Firebase.database("https://testlogin2-a82d6-default-rtdb.firebaseio.com/")
    private var userreviewRef = db.getReference("UserReview")

    private lateinit var adapter : MyReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_review)

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
                    startActivity(Intent(this@MyReview, HomeFragment::class.java))
                    true
                }
                R.id.page_fv -> {
                    // 친구 추가 아이템 클릭 시 친구 추가 화면으로 이동
                    startActivity(Intent(this@MyReview, FriendsList::class.java))
                    true
                }
                R.id.page_ps -> {
                    // 마이페이지 아이템 클릭 시 마이페이지 화면으로 이동
                    startActivity(Intent(this@MyReview, MyPage::class.java))
                    true
                }
                else -> false
            }
        }

        // 아이템 데이터를 itemList에 추가
        // itemList.add(MyReviewClass("떡볶이", 4.5f, "2023-10-03", "맛있는데 양이 좀 적은거 같아요."))

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val userUid = currentUser!!.uid

        val adapter = MyReviewAdapter()
        menu_review.adapter = adapter


        // Firebase Realtime Database에서 리뷰 데이터를 가져옵니다.
        userreviewRef.child(userUid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // 데이터를 순회하며 리뷰 객체를 생성하여 리스트에 추가합니다.
                for (reviewSnapshot in dataSnapshot.children) {
                    val menu = reviewSnapshot.child("menu").getValue(String::class.java)
                    val rating = reviewSnapshot.child("rating").getValue(Float::class.java)
                    val date = reviewSnapshot.child("date").getValue(String::class.java)
                    val contents = reviewSnapshot.child("contents").getValue(String::class.java)

                    adapter.itemList.add(MyReviewClass((menu ?:""), ((rating ?: "")as Float), (date ?: ""), (contents ?: "")))

                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 데이터 가져오기가 실패한 경우 처리할 내용을 여기에 추가하세요.
            }
        })

    }
}
