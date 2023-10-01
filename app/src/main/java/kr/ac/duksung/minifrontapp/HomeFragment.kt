package kr.ac.duksung.minifrontapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import org.w3c.dom.Text

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

        val card1 = findViewById<CardView>(R.id.card1)
        val card2 = findViewById<CardView>(R.id.card2)
        val card3 = findViewById<CardView>(R.id.card3)

        card1.setOnClickListener {
            val intent = Intent(this@HomeFragment, KFoodActivity::class.java)
            startActivity(intent)
        }

        card2.setOnClickListener {
            val intent = Intent(this@HomeFragment, CFoodActivity::class.java)
            startActivity(intent)
        }

        card3.setOnClickListener {
            val intent = Intent(this@HomeFragment, BoonsikActivity::class.java)
            startActivity(intent)
        }
    }
}
