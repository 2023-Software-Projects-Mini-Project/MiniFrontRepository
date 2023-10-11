package kr.ac.duksung.minifrontapp

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_friends_list.*
import kotlinx.android.synthetic.main.activity_friends_list.back_icon
import kotlinx.android.synthetic.main.cfood_list.*


class FriendsList : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var userRef: DatabaseReference
    private val friendsList = mutableListOf<String>()

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
//여기서부터는 바텀
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

        /*      auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        val currentUser = auth.currentUser
        val userId = currentUser?.uid

        if (userId != null) {
            userRef = database.getReference("users").child(userId)
            val layoutManager = LinearLayoutManager(this)
            RCV_friendslist.layoutManager = layoutManager

            val adapter = FriendsAddAdapter(friendsList)
            RCV_friendslist.adapter = adapter

            // 뒤로 가기 아이콘 클릭 시 현재 액티비티 종료
            back_icon.setOnClickListener {
                finish()
            }



            // Firebase Realtime Database에서 친구 목록을 가져와서 리스트에 추가
            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val usersInfo = dataSnapshot.getValue(MainActivity.Usersinfo::class.java)
                    if (usersInfo != null) {
                        val friends = usersInfo.friends
                        if (friends != null) {
                            friendsList.addAll(friends)
                            adapter.notifyDataSetChanged()
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Firebase Realtime Database에서의 읽기 실패 또는 취소 처리
                    println("Database Error: ${databaseError.message}")
                }
            })
            */
    }
