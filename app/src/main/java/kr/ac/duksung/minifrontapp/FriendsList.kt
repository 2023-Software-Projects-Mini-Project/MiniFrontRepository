package kr.ac.duksung.minifrontapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class FriendsList : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var userRef: DatabaseReference
    private val userList = mutableListOf<String>()

    @SuppressLint("MissingInflatedId")
    private lateinit var getUserList: ValueEventListener
    private lateinit var db: FirebaseDatabase
    private lateinit var userAdapter: FriendsAddAdapter

    // Firebase 관련 변수

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends_list)

        val backIcon = findViewById<ImageView>(R.id.back_icon)

        backIcon.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish() // 현재 액티비티 종료
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


        db = FirebaseDatabase.getInstance()

        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        val userRef = db.getReference("users")
        val userList = mutableListOf<String>()

        recyclerView = findViewById(R.id.RCV_friendslist)

        userAdapter = FriendsAddAdapter(userList) // UserAdapter를 초기화
        recyclerView.adapter = userAdapter


        recyclerView.layoutManager = LinearLayoutManager(this)

        getUserList =
            userRef.orderByChild("username").addValueEventListener(object : ValueEventListener {
                // val userList = mutableListOf<String>()
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    val searchButton = findViewById<Button>(R.id.searchButton)
                    val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid

                    searchButton.setOnClickListener {
                        for (userSnapshot in dataSnapshot.children) {
                            val userName =
                                userSnapshot.child("username").getValue(String::class.java)
                            val userUid = userSnapshot.child("userid").getValue(String::class.java)
                            val searchUser = findViewById<EditText>(R.id.searchText).text.toString()

                            if (userName != null && userUid != null && currentUserUid != null) {

                                if (userUid != currentUserUid && userName == searchUser) {
                                    userList.add(userName)
                                    //userAdapter.notifyDataSetChanged()
                                }
                                if (userUid == currentUserUid) {
                                    val newFriendEntry = userRef.child(userName).child("userList")
                                    newFriendEntry.push().setValue(userName)
                                }

                            }

                        }


                        userAdapter.notifyDataSetChanged()
                        //displayUserList(userList)
                        Toast.makeText(
                            this@FriendsList, "친구가 되었습니다", Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })


    }


    // 어댑터를 생성하고 RecyclerView에 설정


}

/*
fun addToCartTest(userUid: String, menuName: String, menuPrice: String, menuCount: Int) {
    val newItem = MenuClass(menuName, menuPrice, menuCount)
    //val cartItemKey = cartRef.child(userUid).push().key

    cartRef.child(userUid).child("$menuName").setValue(newItem)
    Log.d("Cart", "Added to cart: $menuName, $menuPrice")
*/


/*
userRef.orderByChild("username").addListenerForSingleValueEvent(object : ValueEventListener {
    override fun onDataChange(dataSnapshot: DataSnapshot) {
        for (userSnapshot in dataSnapshot.children) {
            val userName = userSnapshot.child("username").getValue(String::class.java)
            if (userName != null) {
                userList.add(userName)
            }
        }
        userAdapter.notifyDataSetChanged()
    }

    override fun onCancelled(error: DatabaseError) {
        // Handle the error
    }
})
*/


/*private fun displayUserList(userList: List<String>) {
    val userListString = userList.joinToString(", ")
    val userListTextView = findViewById<TextView>(R.id.userListTextView)
    userListTextView.text = "User List: $userListString"
}
*/
