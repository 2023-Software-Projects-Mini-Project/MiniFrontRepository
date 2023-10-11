package kr.ac.duksung.minifrontapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

import android.widget.EditText
import android.widget.ImageView

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.review_write.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class ReviewWrite : AppCompatActivity() {

    val db = Firebase.database("https://testlogin2-a82d6-default-rtdb.firebaseio.com/")
    private lateinit var auth: FirebaseAuth
    private val categoriesRef = db.getReference("MenuName")
    private val userreviewRef = db.getReference("UserReview")

    private lateinit var adapter : TotalReviewAdapter

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

        val intent : Intent = intent
        val menuNameText : String = intent.getStringExtra("menuName").toString()


        // 사용자의 UID 가져오기
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val userUid = currentUser?.uid

/*
        if (user != null) {
            val uid = user.uid

            // 사용자 UID를 사용하여 리뷰를 저장
            val reviewsRef = realdb.getReference("MenuName").child("김밥").child("reviews")
            //val userReviewText = "uid 넣지 말라네여"

            btn_finish.setOnClickListener {
                val userReviewText = edit_review.text.toString()

                // 사용자가 입력한 리뷰 텍스트를 가져와서 Firebase에 저장
                reviewsRef.push().setValue(userReviewText)
                saveComment(2.5f, "개빡칠거같아요")
                val currentDate = SimpleDateFormat("yyyy.MM.dd").format(Date())
                savePersonalComment(userUid, "떡볶이", 4.5f, currentDate, "맵찔이도 먹을 수 있어서 좋아요")


              //  findViewById<EditText>(R.id.edit_review).text.clear()
            }
        }

 */
        btn_finish.setOnClickListener {
            val userReviewText = edit_review.text.toString()
            val ratingScore = ratingBar.rating

            // 사용자가 입력한 리뷰 텍스트를 가져와서 Firebase에 저장
            val currentDate = SimpleDateFormat("yyyy.MM.dd").format(Date())
            savePersonalComment(userUid!!, "$menuNameText", ratingScore, currentDate, "$userReviewText")
            saveComment("$menuNameText", ratingScore, "$userReviewText") // 일단 냅둬줘여 리뷰 어케 보내는지 까먹을거 같으니께

            adapter.notifyDataSetChanged()
        }



    }

    // 파이어베이스에 저장
    fun saveComment(menuName: String, rating: Float, contents: String) {
        // comment에 child로 감상평 추가(이때 키 자동 생성, 이 키 얻어옥기)
        var key : String? = categoriesRef.child("$menuName").child("reviews").push().getKey()

        // 객체 생성
        val comment = TotalReviewClass(key!!, rating, contents)
        // 객체를 맵 형으로 변환
        val commentValues : HashMap<String, Any> = comment.toMap()

        // 파이어베이스에 넣어주기(인자에 해시맵과 해시맵에 접근할 수 있는 경로 들어가야함)
        // -> 별도의 해시맵을 만들어줘야함
        val childUpdate : MutableMap<String, Any> = HashMap()
        childUpdate["/reviews/$key"] = commentValues

        categoriesRef.child("$menuName").updateChildren(childUpdate)
    }

    // 파이어베이스에 저장
    fun savePersonalComment(userUid: String, menu: String, rating: Float, date: String, contents: String) {
        val comment = MyReviewClass(menu, rating, date, contents)
        val userreviewItemKey = userreviewRef.child(userUid).push().key
        if (userreviewItemKey != null) {
            userreviewRef.child(userUid).child(userreviewItemKey).setValue(comment)
        }
    }
}