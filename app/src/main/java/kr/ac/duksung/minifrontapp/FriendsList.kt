package kr.ac.duksung.minifrontapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class FriendsList : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends_list)

        // XML에서 정의한 RecyclerView와 SearchView를 찾습니다.
        recyclerView = findViewById(R.id.RCV_friendslist)
        searchView = findViewById(R.id.search_view)

        // RecyclerView 설정
        recyclerView.layoutManager = LinearLayoutManager(this)

        // SearchView의 검색 이벤트 처리
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // 검색 버튼을 누를 때 친구 검색 결과 화면으로 이동
                val intent = Intent(this@FriendsList, FriendsAddList::class.java)
                intent.putExtra("search_query", query)
                startActivity(intent)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        //여기 밑에는 손대지마.

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        // 바텀 네비게이션 아이템 클릭 리스너 설정
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.page_home -> {
                    // 홈 아이템 클릭 시 홈 화면으로 이동
                    startActivity(Intent(this@FriendsList, HomeFragment::class.java))
                    true
                }
                R.id.page_ps -> {
                    // 마이페이지 아이템 클릭 시 친구 추가 화면으로 이동
                    startActivity(Intent(this@FriendsList, MyPage::class.java))
                    true
                }
                else -> false
            }

        }
    }
}
