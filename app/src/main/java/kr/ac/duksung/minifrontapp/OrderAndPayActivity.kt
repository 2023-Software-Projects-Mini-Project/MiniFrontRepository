package kr.ac.duksung.minifrontapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_order_and_pay.*
import kr.ac.duksung.minifrontapp.databinding.ActivityOrderAndPayBinding
import kr.ac.duksung.minifrontapp.databinding.SmartPayContentBinding

import java.text.DecimalFormat

class OrderAndPayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bindingMain = ActivityOrderAndPayBinding.inflate(layoutInflater)        // 현재 페이지와
        val bindingSmartPay = SmartPayContentBinding.inflate(layoutInflater)          //
        setContentView(bindingMain.root)


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



        // 간편결제 버튼 눌리면
        RBT_smartpay.setOnClickListener{
            RBT_smartpay.setTextColor(Color.parseColor("#FFFFFFFF"))
            RBT_Npay.setTextColor(Color.parseColor("#FF000000"))

            bindingMain.SVPay.removeAllViews()                  // 이미 존재하는 자식뷰를 모두 제외(하나의 자식뷰만 가질 수 있으므로)
            bindingMain.SVPay.addView(bindingSmartPay.root)

        }

        //
        RBT_Npay.setOnClickListener{
            RBT_smartpay.setTextColor(Color.parseColor("#FF000000"))
            RBT_Npay.setTextColor(Color.parseColor("#FFFFFFFF"))

            bindingMain.SVPay.removeAllViews()

        }


        Pay_total = Order_total

        BT_pay.setText("$Pay_total 원 결제하기")

        // ~원 결제하기 버튼이 눌리면
        BT_pay.setOnClickListener {
            val intent = Intent(this, WaitingNumActivity::class.java) // Intent는 화면전환 담당객체. Intent(a, b)이면 a에서 b로 화면을 전환.
            startActivity(intent)
        }




    }
}