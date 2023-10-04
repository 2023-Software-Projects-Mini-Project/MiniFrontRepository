package kr.ac.duksung.minifrontapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_cart_main.*

class CartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_main)

        setSupportActionBar(TB_Cart)        // toolbar를 액션바로 적용
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24) // 아이콘 설정

        //val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)      // 장바구니 페이지에서 네비게이션바 홈버튼 색칠 안되도록...
        //bottomNavigationView.selectedItemId = 0 // 선택 해제



        BT_order.setText("21,500원 주문하기")                                // 일단 임의로 가격 직접 표시

        // ~원 주문하기 버튼이 눌리면
        BT_order.setOnClickListener {
            val intent = Intent(this, OrderAndPayActivity::class.java) // Intent는 화면전환 담당객체. Intent(a, b)이면 a에서 b로 화면을 전환.
            startActivity(intent)
        }
    }
}