package kr.ac.duksung.minifrontapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_card.*
import kotlinx.android.synthetic.main.activity_order_details.*
import kotlinx.android.synthetic.main.activity_order_details.bottomNavigationView

class OrderDetails : AppCompatActivity() {

    var orderList : MutableList<OrderDetailsClass> = mutableListOf(
        OrderDetailsClass("9월15일", R.drawable.ttokppokki,"떡볶이", "5,000" ) ,                  // 일단 서버연동 전까지 이렇게 해둠
        OrderDetailsClass("9월15일", R.drawable.ttokppokki,"떡볶이", "5,000" ),
        OrderDetailsClass("9월15일", R.drawable.ttokppokki,"떡볶이", "5,000" )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)

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
                    startActivity(Intent(this@OrderDetails, HomeFragment::class.java))
                    true
                }
                R.id.page_fv -> {
                    // 친구 추가 아이템 클릭 시 친구 추가 화면으로 이동
                    startActivity(Intent(this@OrderDetails, FriendsList::class.java))
                    true
                }
                R.id.page_ps -> {
                    // 마이페이지 아이템 클릭 시 마이페이지 화면으로 이동
                    startActivity(Intent(this@OrderDetails, MyPage::class.java))
                    true
                }
                else -> false
            }
        }

        val adapter = AdapterOrderDetailsDate(orderList)
        order_list.adapter = adapter

    }
}