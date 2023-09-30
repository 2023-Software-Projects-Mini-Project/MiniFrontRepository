package kr.ac.duksung.minifrontapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HomeFragment : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)

        // "View All >" 텍스트뷰를 찾습니다.
        val viewAllButton = findViewById<TextView>(R.id.viewAllButton)

        // 클릭 이벤트 리스너를 설정합니다.
        viewAllButton.setOnClickListener(View.OnClickListener { view: View? ->
            // 이동할 액티비티를 지정하는 Intent를 생성합니다.
            val intent = Intent(this@HomeFragment, TodayMenu::class.java)

            // Intent를 사용하여 액티비티를 시작합니다.
            startActivity(intent)
        })


        // card1 클릭 이벤트 핸들러
        fun onCard1Click(view: View) {
            // kfood_list.xml로 이동하는 Intent를 생성합니다.
            val intent = Intent(this@HomeFragment, KFoodActivity::class.java)

            // Intent를 사용하여 액티비티를 시작합니다.
            startActivity(intent)
        }

        // card2 클릭 이벤트 핸들러
        fun onCard2Click(view: View) {
            // kfood_list.xml로 이동하는 Intent를 생성합니다.
            val intent = Intent(this@HomeFragment, CFoodActivity::class.java)

            // Intent를 사용하여 액티비티를 시작합니다.
            startActivity(intent)
        }

        // card1 클릭 이벤트 핸들러
        fun onCard3Click(view: View) {
            // kfood_list.xml로 이동하는 Intent를 생성합니다.
            val intent = Intent(this@HomeFragment, BoonsikActivity::class.java)

            // Intent를 사용하여 액티비티를 시작합니다.
            startActivity(intent)
        }
    }
}
