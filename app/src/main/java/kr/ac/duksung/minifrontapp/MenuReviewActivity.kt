package kr.ac.duksung.minifrontapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_cart_main.*
import kotlinx.android.synthetic.main.menu_review.*
import kotlinx.android.synthetic.main.menu_review.bottomNavigationView


class MenuReviewActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val db = Firebase.database("https://testlogin2-a82d6-default-rtdb.firebaseio.com/")
    private val cartRef = db.getReference("Cart")
    val menunameRef = db.getReference("MenuName")

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


        var reviewList : MutableList<ReviewClass> = mutableListOf(
            ReviewClass(4.5f, "맛있어요!"), // 일단 서버연동 전까지 이렇게 해둠
            ReviewClass(5.0f, "맛있어요!"),
        )

        val intent : Intent = intent
        val menuNameText = intent.getStringExtra("menuName")
        val menuPriceText = intent.getStringExtra("menuPrice")
        menu_name.setText(menuNameText)


        val adapter = MenuReviewAddAdapter(reviewList)
        menu_review.adapter = adapter

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
}
