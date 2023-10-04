package kr.ac.duksung.minifrontapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_order_and_pay.*
import java.text.DecimalFormat

class OrderAndPayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_and_pay)


        setSupportActionBar(TB_OrderAndPay)        // toolbar를 액션바로 적용
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24) // 아이콘 설정


        var Order_sum = 5
        var Order_total = 21500
        var Pay_total: Int ?= null

        Order_sum_text.setText("$Order_sum 개")                  // 총 주문수량 표시

        val t_dec_up = DecimalFormat("#,###")            // 3자리씩 쉼표 넣어 표시하기 위함
        val print_order_total = t_dec_up.format(Order_total)
        Order_total_text.setText("$print_order_total 원")       // 총 금액 표시


        Pay_total = Order_total

        BT_pay.setText("$Pay_total 원 결제하기")

        // ~원 결제하기 버튼이 눌리면
        BT_pay.setOnClickListener {
            val intent = Intent(this, WaitingNumActivity::class.java) // Intent는 화면전환 담당객체. Intent(a, b)이면 a에서 b로 화면을 전환.
            startActivity(intent)
        }




    }
}