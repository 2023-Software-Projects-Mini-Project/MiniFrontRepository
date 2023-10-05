package kr.ac.duksung.minifrontapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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

        // 친구 목록 데이터를 어댑터에 설정하거나 가져오는 코드를 추가해야 합니다.
        // friendsAdapter.setData(friendsList) 또는 fetchFriendsList()와 같은 메서드를 사용하여 데이터를 설정하세요.
    }
}
