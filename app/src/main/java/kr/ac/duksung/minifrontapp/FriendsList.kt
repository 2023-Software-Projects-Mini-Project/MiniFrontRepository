package kr.ac.duksung.minifrontapp

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_friends_list.*

class FriendsList : AppCompatActivity() {

    // 뷰 홀더에 넣을 가변리스트(추가와 삭제가 자유로움)
    var friendsList : MutableList<FriendsID> = mutableListOf(
        FriendsID("20210691안가은"),                   // 일단 서버연동 전까지 이렇게 해둠
        FriendsID("20210692문나연")
    )



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends_list)


        val adapter = FriendsAddAdapter(friendsList)        // 분할결제를 위한 친구목록 띄우기용 adapter
        RCV_friendslist.adapter = adapter                           // 이렇게 하면 항상 리사이클러뷰가 보이니까


        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        // 바텀 네비게이션 아이템 클릭 리스너 설정
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.page_home -> {
                    // 친구 추가 아이템 클릭 시 친구 추가 화면으로 이동
                    startActivity(Intent(this@FriendsList, HomeFragment::class.java))
                    true
                }
                R.id.page_ps -> {
                    // 마이페이지 아이템 클릭 시 마이페이지 화면으로 이동
                    startActivity(Intent(this@FriendsList, MyPage::class.java))
                    true
                }
                else -> false
            }
        }
    }
}