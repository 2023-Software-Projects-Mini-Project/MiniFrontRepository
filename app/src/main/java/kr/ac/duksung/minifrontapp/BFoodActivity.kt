package kr.ac.duksung.minifrontapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.boonski_list.*
import kotlinx.android.synthetic.main.boonski_list.back_icon
import kotlinx.android.synthetic.main.boonski_list.bottomNavigationView
import kotlinx.android.synthetic.main.cfood_list.*

class BFoodActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.boonski_list)

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
                    startActivity(Intent(this@BFoodActivity, HomeFragment::class.java))
                    true
                }
                R.id.page_fv -> {
                    // 친구 추가 아이템 클릭 시 친구 추가 화면으로 이동
                    startActivity(Intent(this@BFoodActivity, FriendsList::class.java))
                    true
                }
                R.id.page_ps -> {
                    // 마이페이지 아이템 클릭 시 마이페이지 화면으로 이동
                    startActivity(Intent(this@BFoodActivity, MyPage::class.java))
                    true
                }
                else -> false
            }
        }

        goKimbab.setOnClickListener{
            val intent = Intent(this@BFoodActivity, MenuReviewActivity::class.java)
            intent.putExtra("menuName", "김밥")
            startActivity(intent)
        }

        goRamen.setOnClickListener{
            val intent = Intent(this@BFoodActivity, MenuReviewActivity::class.java)
            intent.putExtra("menuName", "라면")
            startActivity(intent)
        }

        goTtokppokki.setOnClickListener{
            val intent = Intent(this@BFoodActivity, MenuReviewActivity::class.java)
            intent.putExtra("menuName", "떡볶이")
            startActivity(intent)
        }
    }
}