package kr.ac.duksung.minifrontapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.menu_review.*

// 음식 상세 페이지
class MenuReviewActivity : AppCompatActivity() {

    private lateinit var iv: ImageView

    private lateinit var auth: FirebaseAuth
    private val db = Firebase.database("https://testlogin2-a82d6-default-rtdb.firebaseio.com/")
    private val cartRef = db.getReference("Cart")
    private val categoriesRef = db.getReference("MenuName")

    private lateinit var adapter : TotalReviewAdapter


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

        val intent : Intent = intent
        val menuNameText : String = intent.getStringExtra("menuName").toString()
        val menuPriceText : String = intent.getStringExtra("menuPrice").toString()
        menu_name.setText(menuNameText)


        iv = findViewById(R.id.image_view)

        val firebaseStorage = FirebaseStorage.getInstance()     // 1. Firebase Storage 관리 객체 얻어오기
        val rootRef = firebaseStorage.reference                 // 2. 최상위 노드 참조 객체 얻어오기
        // 메뉴 이미지 띄우기
        var image : String
        categoriesRef.child("$menuNameText").get().addOnSuccessListener {
            image = it.child("menuImage").getValue(String::class.java).toString()
            rootRef.child("$image").downloadUrl.addOnSuccessListener { uri ->
                // 다운로드 URL이 파라미터로 전달되어 옴.
                Glide.with(image_view).load(uri).into(iv)
            }
        }

        adapter = TotalReviewAdapter()
        menu_review.adapter = adapter

        // 메뉴별 리뷰 데이터 가져오기
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
        ratingbar.rating = adapter.totalRating      // 레이팅바 별점 연결


        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val userUid = currentUser?.uid



        var exist : Boolean = false      // 장바구니에 담으려는 메뉴와 같은 이름의 메뉴가 있는지 확인 및 저장용 변수
        addToCartButton.setOnClickListener {

            if (menuNameText != null && menuPriceText != null && userUid != null) {
                Log.e("MeneReviewActivity: ", "$menuNameText, $menuPriceText, $userUid")

                // 장바구니 순회해해서 같은 아이템이 있는지 봄
                cartRef.child(userUid).addListenerForSingleValueEvent(object: ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        Log.e("MeneReviewActivity: ", "override fun onDataChange")
                        if (snapshot.exists()){
                            Log.e("MeneReviewActivity: ", "스냅샷 있음")

                            var oldcount : Int

                            // userid 아래 장바구니에 담아둔 메뉴 순회 시작
                            for (childSnapshot in snapshot.children){
                                var oldmenu = childSnapshot.child("menuName").getValue(String::class.java).toString()
                                Log.e("MeneReviewActivity: ", "$oldmenu, $menuNameText")

                                if (menuNameText == oldmenu){        // 담으려는 메뉴가 장바구니에 있으면
                                    Log.e("MeneReviewActivity: ", "장바구니에 추가 안한다")
                                    exist = true

                                    oldcount = childSnapshot.child("menuCount").getValue(Int::class.java)!! // 담으려는 메뉴의 기존 수량 가져옴
                                    oldcount += 1       // 기존 수량 +1

                                    var key = childSnapshot.key

                                    val updateCount: HashMap<String, Any> = HashMap()       // updateChildren은 인스턴스로 해쉬맵만 받음
                                    updateCount["menuCount"] = oldcount                     // key: menuCount, value: oldcount로 updateCount에 저장

                                    // 루트 노드부터 타고 내려오면서 변화된 수량을 DB에 저장
                                    cartRef.child(userUid).child("$key").updateChildren(updateCount)
                                    Toast.makeText(this@MenuReviewActivity, "$menuNameText 1인분 추가", Toast.LENGTH_SHORT).show()

                                    break
                                }

                                else{                   // 담으려는 메뉴가 장바구니에 없으면
                                    exist = false
                                    Log.e("MeneReviewActivity: ", "장바구니에 추가한다")
                                }
                            }

                            if(exist == false){
                                addToCartTest(userUid, menuNameText, menuPriceText, 1)

                                Toast.makeText(this@MenuReviewActivity, "$menuNameText 1인분 추가", Toast.LENGTH_SHORT).show()
                                Log.e("MeneReviewActivity: ", "장바구니에 추가한다")
                            }
                            Log.e("MeneReviewActivity: ", "순환끝")
                        }
                        else{
                            Log.e("MeneReviewActivity: ", "스냅샷 없음")
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        Log.e("test", "loadItem:onCancelled : ${error.toException()}")
                    }
                })

            } else {
                // 유효한 메뉴 정보가 없을 경우 예외 처리
                // 사용자에게 적절한 알림을 표시하거나 로그를 남길 수 있습니다.
            }
            finish()
        }

        addToReviewButton.setOnClickListener {
            val reviewIntent = Intent(this@MenuReviewActivity, ReviewWrite::class.java)
            reviewIntent.putExtra("menuName", menuNameText)
            startActivity(reviewIntent)
        }


    }


    // 사용자 로그인 및 UID 얻기
    fun addToCart(userUid: String, menuName: String, menuPrice: String, menuCount: Int) {
        val newItem = MenuClass(menuName, menuPrice, menuCount)
        val cartItemKey = cartRef.child(userUid).push().key
        if (cartItemKey != null) {
            cartRef.child(userUid).child(cartItemKey).setValue(newItem)
            Log.d("Cart", "Added to cart: $menuName, $menuPrice")
        }
    }

    // 사용자 로그인 및 UID 얻기
    fun addToCartTest(userUid: String, menuName: String, menuPrice: String, menuCount: Int) {
        val newItem = MenuClass(menuName, menuPrice, menuCount)
        //val cartItemKey = cartRef.child(userUid).push().key

        cartRef.child(userUid).child("$menuName").setValue(newItem)
        Log.d("Cart", "Added to cart: $menuName, $menuPrice")

    }
}