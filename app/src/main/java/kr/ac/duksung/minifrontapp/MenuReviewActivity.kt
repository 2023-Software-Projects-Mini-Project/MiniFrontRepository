package kr.ac.duksung.minifrontapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.menu_review.*
import kotlinx.android.synthetic.main.menu_review.bottomNavigationView


class MenuReviewActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val db = Firebase.database("https://testlogin2-a82d6-default-rtdb.firebaseio.com/")
    private val cartRef = db.getReference("Cart")
    private var categoriesRef = db.getReference("MenuName")

    private lateinit var adapter : TotalReviewAdapter


    data class CartItem(val menuName: String, val menuPrice: String)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_review)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val userUid = currentUser?.uid

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

        val intent : Intent = intent
        val menuNameText = intent.getStringExtra("menuName")
        val menuPriceText = intent.getStringExtra("menuPrice")
        menu_name.setText(menuNameText)



        adapter = TotalReviewAdapter()
        menu_review.adapter = adapter

        //var totalrating: Float = 0.0f


        categoriesRef.child("$menuNameText").child("reviews").addValueEventListener(object: ValueEventListener {
            // 내용 추가될 때마다 자동으로 화면 바뀌게
            override fun onDataChange(snapshot: DataSnapshot) { // snapshot : 데이터베이스에서 조회되는 객체들을 접근할 수 있는 권한이 있는 객체
                Log.d("MenuReview: ", "onDataChage Success")
                if (snapshot.exists()){

                    var totalRating: Float = 0.0f  // 리뷰 총 별점을 저장할 변수
                    // 리뷰 데이터를 가져오기
                    for (childSnapshot in snapshot.children) {
                        val objectId = childSnapshot.child("objectID").getValue(String::class.java)
                        val rating = childSnapshot.child("rating").getValue(Float::class.java)
                        val contents = childSnapshot.child("contents").getValue(String::class.java)

                        // 각 리뷰의 별점을 총 별점에 더함
                        totalRating += rating ?: 0.0f  // null 처리

                        //totalrating += rating!!
                        adapter.itemList.add(TotalReviewClass((objectId ?:""), ((rating ?: "")as Float), (contents ?: "")))

                    }
                    adapter.notifyDataSetChanged()

                    // 리뷰의 총 별점을 리뷰 개수로 나누어 평균을 계산
                    val averageRating = totalRating / snapshot.childrenCount
                    // 계산된 평균 별점을 ratingbar에 설정
                    ratingbar.rating = averageRating

                }
            }

            // 취소되었을 때
            override fun onCancelled(error: DatabaseError) {
                Log.e("test", "loadItem:onCancelled : ${error.toException()}")
            }
        })

        ratingbar.rating = adapter.totalRating


        // 장바구니에 담기 버튼이 눌렸을때
        addToCartButton.setOnClickListener {
            if (menuNameText != null && menuPriceText != null && userUid != null) {
                // 장바구니에 메뉴 추가
                addToCart(menuNameText, menuPriceText, userUid)
                val mainIntent = Intent(this, CartActivity::class.java)
                startActivity(mainIntent)
            } else {
                // 유효한 메뉴 정보가 없을 경우 예외 처리
                // 사용자에게 적절한 알림을 표시하거나 로그를 남길 수 있습니다.
            }
        }

    }
    // 사용자 로그인 및 UID 얻기
    private fun addToCart(menuName: String, menuPrice: String, userUid: String) {
        val newItem = CartItem(menuName, menuPrice)
        val cartItemKey = cartRef.child(userUid).push().key
        if (cartItemKey != null) {
            cartRef.child(userUid).child(cartItemKey).setValue(newItem)
            Log.d("Cart", "Added to cart: $menuName, $menuPrice")
        }
    }

    // 파이어베이스에 저장
    fun saveComment(rating: Float, contents: String) {
        // comment에 child로 감상평 추가(이때 키 자동 생성, 이 키 얻어옥기)
        var key : String? = categoriesRef.child("menuNameText").child("reviews").push().getKey()

        // 객체 생성
        val comment = TotalReviewClass(key!!, rating, contents)
        // 객체를 맵 형으로 변환
        val commentValues : HashMap<String, Any> = comment.toMap()

        // 파이어베이스에 넣어주기(인자에 해시맵과 해시맵에 접근할 수 있는 경로 들어가야함)
        // -> 별도의 해시맵을 만들어줘야함
        val childUpdate : MutableMap<String, Any> = HashMap()
        childUpdate["/reviews/$key"] = commentValues

        categoriesRef.child("menuNameText").updateChildren(childUpdate)
    }

    /*
    // 이 함수를 이용하고 싶었는데 잘 안되더라구요,,,
    // 데이터베이스로 접근되는 데이터 관리하는 클래스
    fun loadCommentList(dataSanpshot : DataSnapshot) {
        // comments에서 쭉 내려옴
        val collectionIterator = dataSanpshot.children.iterator()     // reviews 아래로
        // comments가 있다 == 한줄평이 존재한다
        if (collectionIterator.hasNext()) {                             // reviews 아래 항목이 존재한다면
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
                //val rating = map["rating"] as Float
                val contents = map["contents"] as String
                //Toast.makeText(this@MenuReviewActivity, "읽어옴", Toast.LENGTH_SHORT).show()
                //Log.d("MenuReview", "Toasting")
                // 리사이클러뷰에 연결
                adapter.itemList.add(TotalReviewClass(objectId, 4.0f, contents))
            }
            // 데이터 바뀌었다고 알려주기
            adapter.notifyDataSetChanged()
        }
    }
 */

}
