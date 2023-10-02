package kr.ac.duksung.minifrontapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.SearchView
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

        // 검색창 뷰를 찾습니다.
        val searchView = findViewById<SearchView>(R.id.search_view)

        // SearchView의 리스너를 설정합니다.
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // 여기에서 검색어(query)를 가지고 검색 결과를 처리하고 메뉴 화면으로 이동하도록 구현합니다.
                if (!query.isNullOrBlank()) {
                    // 예를 들어, query를 사용하여 검색 결과 화면으로 이동하는 Intent를 생성합니다.
                    val intent = Intent(this@HomeFragment, MenuReviewActivity::class.java)
                    intent.putExtra("menu_name", query) // 검색어를 인텐트에 추가할 수 있습니다.
                    startActivity(intent)
                    return true
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // 검색어가 변경될 때 처리할 내용을 추가할 수 있습니다.
                return true
            }
        })

    }

}
