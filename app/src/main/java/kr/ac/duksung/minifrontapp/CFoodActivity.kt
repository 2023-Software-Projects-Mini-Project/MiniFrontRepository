package kr.ac.duksung.minifrontapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_card.*
import kotlinx.android.synthetic.main.cfood_list.*
import kotlinx.android.synthetic.main.cfood_list.back_icon
import kotlinx.android.synthetic.main.cfood_list.bottomNavigationView

class CFoodActivity : AppCompatActivity() {

    private val db = Firebase.database("https://testlogin2-a82d6-default-rtdb.firebaseio.com/")
    private val menunameRef = db.getReference("MenuName")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cfood_list)

        back_icon.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish() // 현재 액티비티 종료
            }
        })

        // 바텀 네비게이션 아이템 클릭 리스너 설정
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.page_home -> {
                    // 홈 아이템 클릭 시 홈 화면으로 이동
                    startActivity(Intent(this@CFoodActivity, HomeFragment::class.java))
                    true
                }
                R.id.page_fv -> {
                    // 친구 추가 아이템 클릭 시 친구 추가 화면으로 이동
                    startActivity(Intent(this@CFoodActivity, FriendsList::class.java))
                    true
                }
                R.id.page_ps -> {
                    // 마이페이지 아이템 클릭 시 마이페이지 화면으로 이동
                    startActivity(Intent(this@CFoodActivity, MyPage::class.java))
                    true
                }
                else -> false
            }
        }

        goJajangmyeon.setOnClickListener {
            val intent = Intent(this@CFoodActivity, MenuReviewActivity::class.java)
            intent.putExtra("menuName", "짜장면")
            startActivity(intent)
        }

        goJjamppong.setOnClickListener{
            val intent = Intent(this@CFoodActivity, MenuReviewActivity::class.java)
            intent.putExtra("menuName", "짬뽕")
            startActivity(intent)
        }

        goTangsuyuk.setOnClickListener{
            val intent = Intent(this@CFoodActivity, MenuReviewActivity::class.java)
            intent.putExtra("menuName", "탕수육")
            startActivity(intent)
        }



    }
}