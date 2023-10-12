package kr.ac.duksung.minifrontapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_order_and_pay.*
import kotlinx.android.synthetic.main.activity_order_and_pay.back_icon
import kotlinx.android.synthetic.main.activity_order_and_pay.bottomNavigationView
import kotlinx.android.synthetic.main.activity_waiting_num.*
import java.text.DecimalFormat

class WaitingNumActivity : AppCompatActivity() {

    private val db = Firebase.database("https://testlogin2-a82d6-default-rtdb.firebaseio.com/")
    private val totalorderRef = db.getReference("TotalOrder")

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_waiting_num)

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
                    startActivity(Intent(this@WaitingNumActivity, HomeFragment::class.java))
                    true
                }
                R.id.page_fv -> {
                    // 친구 추가 아이템 클릭 시 친구 추가 화면으로 이동
                    startActivity(Intent(this@WaitingNumActivity, FriendsList::class.java))
                    true
                }
                R.id.page_ps -> {
                    // 마이페이지 아이템 클릭 시 마이페이지 화면으로 이동
                    startActivity(Intent(this@WaitingNumActivity, MyPage::class.java))
                    true
                }
                else -> false
            }
        }

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val userUid = currentUser!!.uid

        var index : String

        totalorderRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot){

                // var index : String

                for(childSnapshot in snapshot.children){
                    val userID = childSnapshot.child("userid").getValue(String::class.java)

                    if(userID == userUid){
                        index = childSnapshot.child("OrderNum").getValue(String::class.java).toString()
                        Order_num_text.setText("$index")
                        Log.d("test", "$index")
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("test", "loadItem:onCancelled : ${error.toException()}")
            }
        })



    }
}