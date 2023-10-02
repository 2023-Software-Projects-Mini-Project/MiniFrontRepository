package kr.ac.duksung.minifrontapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_order_and_pay.*
import kr.ac.duksung.minifrontapp.databinding.ActivityOrderAndPayBinding
import kr.ac.duksung.minifrontapp.databinding.SmartPayContentBinding
import java.text.DecimalFormat

class OrderAndPayActivity : AppCompatActivity() {

    // 뷰 홀더에 넣을 가변리스트(추가와 삭제가 자유로움)
    var friendsList : MutableList<FriendsID> = mutableListOf(
        FriendsID("20210691안가은"),                   // 일단 서버연동 전까지 이렇게 해둠
        FriendsID("20210692문나연")
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bindingMain = ActivityOrderAndPayBinding.inflate(layoutInflater)        // 현재 페이지와
        val bindingSmartPay = SmartPayContentBinding.inflate(layoutInflater)            //
        setContentView(bindingMain.root)


        setSupportActionBar(TB_OrderAndPay)        // toolbar를 액션바로 적용
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24) // 아이콘 설정

        val adapter = FriendsRowAdapter(friendsList)        // 분할결제를 위한 친구목록 띄우기용 adapter
        RCV_pay.adapter = adapter                           // 이렇게 하면 항상 리사이클러뷰가 보이니까
        RCV_pay.visibility = View.INVISIBLE                 // visibility = INVISIBLE로 일단 안보이게 처리


        var Order_sum = 5               // 총 주문수량
        var Order_total = 21500         // 총 결제금액
        var Pay_total: Int ?= null      // 결제하기 버튼에 표시될 금액

        Order_sum_text.setText("$Order_sum 개")                  // 총 주문수량 표시

        val t_dec_up = DecimalFormat("#,###")            // 3자리씩 쉼표 넣어 표시하기 위함
        val print_order_total = t_dec_up.format(Order_total)
        Order_total_text.setText("$print_order_total 원")       // 총 금액 표시



        // 간편결제 버튼 눌리면
        RBT_smartpay.setOnClickListener{
            RBT_smartpay.setTextColor(Color.parseColor("#FFFFFFFF"))
            RBT_Npay.setTextColor(Color.parseColor("#FF000000"))

            SV_pay.visibility = View.VISIBLE                    // 스크롤뷰(결제방법) 보이게
            RCV_pay.visibility = View.INVISIBLE                 // 리사이클러뷰(함께 결제할 친구목록) 안 보이게
            bindingMain.SVPay.removeAllViews()                  // 이미 존재하는 자식뷰를 모두 제외(스크롤뷰는 하나의 자식뷰만 가질 수 있으므로)
            bindingMain.SVPay.addView(bindingSmartPay.root)

        }

        RBT_Npay.setOnClickListener{
            RBT_smartpay.setTextColor(Color.parseColor("#FF000000"))
            RBT_Npay.setTextColor(Color.parseColor("#FFFFFFFF"))

            SV_pay.visibility = View.INVISIBLE                   // 스크롤뷰(결제방법) 안 보이게
            RCV_pay.visibility = View.VISIBLE                    // 리사이클러뷰(함께 결제할 친구목록) 보이게
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