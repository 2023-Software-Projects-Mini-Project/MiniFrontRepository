package kr.ac.duksung.minifrontapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_order_details.*

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

        val adapter = AdapterOrderDetailsDate(orderList)
        order_list.adapter = adapter

    }
}