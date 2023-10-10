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
import kotlinx.android.synthetic.main.activity_card.bottomNavigationView
import kotlinx.android.synthetic.main.menu_review.*

class MyReview : AppCompatActivity() {

    // itemList를 초기화합니다.
    private val itemList: MutableList<MyReviewClass> = mutableListOf()

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

        // Adapter를 itemList와 함께 초기화
        val adapter = MyReviewAdapter(itemList)
        menu_review.adapter = adapter

        // Firebase Realtime Database의 루트 참조를 가져옵니다.
        val database = FirebaseDatabase.getInstance()
        val databaseReference = database.reference

// 현재 사용자의 인증 토큰을 가져옵니다. (Firebase Authentication 필요)
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userToken = currentUser?.getIdToken(false)?.result?.token

        // 리뷰 데이터를 가져오는 함수
        fun fetchReviews() {
            // Firebase Realtime Database에서 리뷰 데이터를 가져옵니다.
            databaseReference.child("reviews").addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val reviewsList: MutableList<MyReviewClass> = mutableListOf()

                    // 데이터를 순회하며 리뷰 객체를 생성하여 리스트에 추가합니다.
                    for (reviewSnapshot in dataSnapshot.children) {
                        val menu = reviewSnapshot.child("menu").getValue(String::class.java)
                        val rating = reviewSnapshot.child("rating").getValue(Float::class.java)
                        val date = reviewSnapshot.child("date").getValue(String::class.java)
                        val reviewText = reviewSnapshot.child("contents").getValue(String::class.java)

                        if (menu != null && rating != null && date != null && reviewText != null) {
                            val review = MyReviewClass(menu, rating, date, reviewText)
                            reviewsList.add(review)
                        }
                    }

                    // 가져온 리뷰 데이터를 RecyclerView에 표시합니다.
                    val adapter = MyReviewAdapter(reviewsList)
                    menu_review.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // 데이터 가져오기가 실패한 경우 처리할 내용을 여기에 추가하세요.
                }
            })
        }

// fetchReviews 함수를 호출하여 리뷰 데이터를 가져옵니다.
        fetchReviews()

    }
}
