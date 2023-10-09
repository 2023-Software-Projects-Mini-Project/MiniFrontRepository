package kr.ac.duksung.minifrontapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.cfood_list.back_icon
import kotlinx.android.synthetic.main.activity_card.*
import kotlinx.android.synthetic.main.kfood_list.*
import kotlinx.android.synthetic.main.kfood_list.bottomNavigationView

class KFoodActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kfood_list)


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
                    startActivity(Intent(this@KFoodActivity, HomeFragment::class.java))
                    true
                }
                R.id.page_fv -> {
                    // 친구 추가 아이템 클릭 시 친구 추가 화면으로 이동
                    startActivity(Intent(this@KFoodActivity, FriendsList::class.java))
                    true
                }
                R.id.page_ps -> {
                    // 마이페이지 아이템 클릭 시 마이페이지 화면으로 이동
                    startActivity(Intent(this@KFoodActivity, MyPage::class.java))
                    true
                }
                else -> false
            }
        }

        goKimchi.setOnClickListener {
            val intent = Intent(this@KFoodActivity, MenuReviewActivity::class.java)
            intent.putExtra("menuName", "김치찌개")
            intent.putExtra("menuPrice", "5500")
            startActivity(intent)
        }

        goDoenjang.setOnClickListener{
            val intent = Intent(this@KFoodActivity, MenuReviewActivity::class.java)
            intent.putExtra("menuName", "된장찌개")
            intent.putExtra("menuPrice", "5500")
            startActivity(intent)
        }

        goBoodea.setOnClickListener {
            val intent = Intent(this@KFoodActivity, MenuReviewActivity::class.java)
            intent.putExtra("menuName", "부대찌개")
            intent.putExtra("menuPrice", "5500")
            startActivity(intent)
        }
    }
}
