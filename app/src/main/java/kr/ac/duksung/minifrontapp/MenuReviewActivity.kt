package kr.ac.duksung.minifrontapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.menu_review.*
import kotlinx.android.synthetic.main.menu_review.bottomNavigationView

class MenuReviewActivity : AppCompatActivity() {

    private lateinit var adapter : TotalReviewAdapter
    private val db = Firebase.database("https://testlogin2-a82d6-default-rtdb.firebaseio.com/")
    private val categoriesRef = db.getReference("MenuName")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_review)

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
                    startActivity(Intent(this@MenuReviewActivity, HomeFragment::class.java))
                    true
                }
                R.id.page_fv -> {
                    // 친구 추가 아이템 클릭 시 친구 추가 화면으로 이동
                    startActivity(Intent(this@MenuReviewActivity, FriendsList::class.java))
                    true
                }
                R.id.page_ps -> {
                    // 마이페이지 아이템 클릭 시 마이페이지 화면으로 이동
                    startActivity(Intent(this@MenuReviewActivity, MyPage::class.java))
                    true
                }
                else -> false
            }
        }

/*
        var reviewList : MutableList<TotalReviewClass> = mutableListOf(
            TotalReviewClass(4.5f, "맛있어요!"), // 일단 서버연동 전까지 이렇게 해둠
            TotalReviewClass(5.0f, "맛있어요!"),
        )

 */

        val intent : Intent = intent
        val menuNameText = intent.getStringExtra("menuName")
        menu_name.setText(menuNameText)

/*
        categoriesRef.child("김밥").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {

                    /*
                    reviewList.add(
                        ReviewClass(
                        4.5f, dataSnapshot.child("reviews").child("0").getValue(String::class.java)
                    ))
                     */
                    Toast.makeText(this@MenuReviewActivity, dataSnapshot.child("reviews").child("0").getValue(String::class.java), Toast.LENGTH_SHORT).show()

                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 데이터 읽기가 취소된 경우
                Log.e("Firebase", "Data read cancelled: ${databaseError.message}")
            }
        })

 */


        adapter = TotalReviewAdapter()
        menu_review.adapter = adapter

        //saveComment(2.5f, "개빡칠거같아요")

        // 최근에 저잘된 데이터 5개 불러오기
        categoriesRef.child("김밥").limitToFirst(5).addValueEventListener(object: ValueEventListener {
            // 내용 추가될 때마다 자동으로 화면 바뀌게
            override fun onDataChange(snapshot: DataSnapshot) { // snapshot : 데이터베이스에서 조회되는 객체들을 접근할 수 있는 권한이 있는 객체
                loadCommentList(snapshot)
            }

            // 취소되었을 때
            override fun onCancelled(error: DatabaseError) {
                Log.e("test", "loadItem:onCancelled : ${error.toException()}")
            }
        })

    }

    // 데이터베이스로 접근되는 데이터 관리하는 클래스
    fun loadCommentList(dataSanpshot : DataSnapshot) {
        // comments에서 쭉 내려옴
        val collectionIterator = dataSanpshot!!.children.iterator()
        // comments가 있다 == 한줄평이 존재한다
        if (collectionIterator.hasNext()) {
            // 예전 아이템 지우기
            adapter.itemList.clear()
            // 모든 한줄평 읽어오기
            val comments = collectionIterator.next()
            val itemsIterator = comments.children.iterator()
            while (itemsIterator.hasNext()) {
                // 매 반복마다 itemsIterator가 가리키는 아이템 가져오기
                val currentItem = itemsIterator.next()
                // 해시맵 형태로 읽어오기(저장도 해시맵 형태로 해야하니까)
                val map = currentItem.value as HashMap<String, Any>
                // 데이터 변수로 만들기
                val objectId = map["objectId"].toString()
                val rating = map["rating"] as Float
                val contents = map["contents"] as String
                // 리사이클러뷰에 연결
                adapter.itemList.add(TotalReviewClass(objectId, rating, contents))
            }
            // 데이터 바뀌었다고 알려주기
            adapter.notifyDataSetChanged()
        }
    }

    // 파이어베이스에 저장
    fun saveComment(rating: Float, contents: String) {
        // comment에 child로 감상평 추가(이때 키 자동 생성, 이 키 얻어옥기)
        var key : String? = categoriesRef.child("김밥").child("reviews").push().getKey()

        // 객체 생성
        val comment = TotalReviewClass(key!!, rating, contents)
        // 객체를 맵 형으로 변환
        val commentValues : HashMap<String, Any> = comment.toMap()

        // 파이어베이스에 넣어주기(인자에 해시맵과 해시맵에 접근할 수 있는 경로 들어가야함)
        // -> 별도의 해시맵을 만들어줘야함
        val childUpdate : MutableMap<String, Any> = HashMap()
        childUpdate["/reviews/$key"] = commentValues

        categoriesRef.child("김밥").updateChildren(childUpdate)
    }
}
