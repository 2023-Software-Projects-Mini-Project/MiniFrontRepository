package kr.ac.duksung.minifrontapp

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_home.*
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : AppCompatActivity() {

    private val database = FirebaseDatabase.getInstance()
    private val categoriesRef = database.getReference("MenuName")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)


        // 장바구니 아이콘 클릭시
        cart_icon.setOnClickListener{
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }


        // "View All >" 텍스트뷰를 찾습니다.
        val viewAllButton = findViewById<TextView>(R.id.viewAllButton)

        // 클릭 이벤트 리스너를 설정합니다.
        viewAllButton.setOnClickListener(View.OnClickListener { view: View? ->
            // 이동할 액티비티를 지정하는 Intent를 생성합니다.
            val intent = Intent(this@HomeFragment, TodayMenu::class.java)

            // Intent를 사용하여 액티비티를 시작합니다.
            startActivity(intent)
        })



        val textView: TextView = findViewById(R.id.today_date) // R.id.today_date는 텍스트뷰의 ID입니다.

        // 현재 날짜를 가져오고 원하는 형식으로 포맷합니다.
        val currentDate = SimpleDateFormat("yyyy.MM.dd").format(Date())

        // 텍스트뷰에 현재 날짜를 설정합니다.
        textView.text = currentDate


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
            val intent = Intent(this@HomeFragment, BFoodActivity::class.java)
            startActivity(intent)
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        // 바텀 네비게이션 아이템 클릭 리스너 설정
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.page_fv -> {
                    // 친구 추가 아이템 클릭 시 친구 추가 화면으로 이동
                    startActivity(Intent(this@HomeFragment, FriendsList::class.java))
                    true
                }
                R.id.page_ps -> {
                    // 마이페이지 아이템 클릭 시 마이페이지 화면으로 이동
                    startActivity(Intent(this@HomeFragment, MyPage::class.java))
                    true
                }
                else -> false
            }
        }

        val todayDate = getTodayDate()
        categoriesRef.child("오늘의 메뉴").child(todayDate)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val TodayATextView = findViewById<TextView>(R.id.mon_A)
                        val TodayBTextView = findViewById<TextView>(R.id.mon_B)

                        TodayATextView.text = dataSnapshot.child("menuA").child("todayName").getValue(String::class.java)
                        TodayBTextView.text = dataSnapshot.child("menuB").child("todayName").getValue(String::class.java)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // 필요한 대로 onCancelled를 처리합니다.
                }
            })
    }

    private fun getTodayDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }
}