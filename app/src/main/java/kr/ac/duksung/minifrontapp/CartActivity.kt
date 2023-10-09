package kr.ac.duksung.minifrontapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_card.*
import kotlinx.android.synthetic.main.activity_cart_main.*
import kotlinx.android.synthetic.main.activity_cart_main.back_icon
import kotlinx.android.synthetic.main.activity_cart_main.bottomNavigationView
import kr.ac.duksung.minifrontapp.MenuReviewActivity.CartItem

class CartActivity : AppCompatActivity() {

/*
    var menuList : MutableList<MenuClass> = mutableListOf(
        MenuClass("떡볶이", "5,000", "1", R.drawable.boonsik),                   // 일단 서버연동 전까지 이렇게 해둠
        MenuClass("된장찌개", "5,500", "2", R.drawable.doenjang),
        MenuClass("된장찌개", "5,500", "2", R.drawable.doenjang),
        MenuClass("된장찌개", "5,500", "2", R.drawable.doenjang),
        MenuClass("된장찌개", "5,500", "2", R.drawable.doenjang)
    )
*/
    // CartActivity 객체화(다른 클래스에서도 참조할 수 있도록)
    init{
        var instance = this
    }

    companion object{
        private var instance:CartActivity? = null
        fun getInstance(): CartActivity? {
            return instance
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_main)

        back_icon.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish() // 현재 액티비티 종료
            }
        })

        // 바텀 네비게이션 아이템 클릭 리스너 설정
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.page_home -> {
                    // 홈 아이템 클릭 시 홈 화면으로 이동
                    startActivity(Intent(this@CartActivity, HomeFragment::class.java))
                    true
                }
                R.id.page_fv -> {
                    // 친구 추가 아이템 클릭 시 친구 추가 화면으로 이동
                    startActivity(Intent(this@CartActivity, FriendsList::class.java))
                    true
                }
                R.id.page_ps -> {
                    // 마이페이지 아이템 클릭 시 마이페이지 화면으로 이동
                    startActivity(Intent(this@CartActivity, MyPage::class.java))
                    true
                }
                else -> false
            }
        }



        //val adapter = CartAddAdapter(this, MenuReviewActivity.CartManager.cartItems)
        //RCV_menu.adapter = adapter



        BT_order.setText("9,000원 주문하기")                                // 일단 임의로 가격 직접 표시

        // ~원 주문하기 버튼이 눌리면
        BT_order.setOnClickListener {
            val intent = Intent(this, OrderAndPayActivity::class.java) // Intent는 화면전환 담당객체. Intent(a, b)이면 a에서 b로 화면을 전환.
            startActivity(intent)
        }
    }
}