package kr.ac.duksung.minifrontapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_cart_main.*
import kotlinx.android.synthetic.main.row_menu.view.*
import java.text.DecimalFormat


class CartActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val db = Firebase.database("https://testlogin2-a82d6-default-rtdb.firebaseio.com/")
    private val cartRef = db.getReference("Cart")

    private lateinit var adapter : CartAddAdapter
    lateinit var mContext: Context          // Context 변수 선언
    lateinit var USERID : String


    // CartActivity 객체화(다른 클래스에서도 참조할 수 있도록)
    init{
        var instance = this
    }

    companion object{
        private var instance:CartActivity? = null
        fun getInstance(): CartActivity? {
            return instance
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_main)

        mContext = this

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
                    startActivity(Intent(this@CartActivity, HomeFragment::class.java))
                    true
                }
                R.id.page_fv -> {
                    // 친구 추가 아이템 클릭 시 친구 추가 화면으로 이동
                    startActivity(Intent(this@CartActivity, FriendsList::class.java))
                    true
                }
                R.id.page_ps -> {
                    // 마이페이지 아이템 클릭 시 마이페이지 화면으로 이동
                    startActivity(Intent(this@CartActivity, MyPage::class.java))
                    true
                }
                else -> false
            }
        }


        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val userUid = currentUser!!.uid

        if (userUid != null) {      // 어댑터로 보낼 userid
            USERID = userUid
        }

        adapter = CartAddAdapter()
        RCV_menu.adapter = adapter
        val intent = Intent(this, OrderAndPayActivity::class.java)


        cartRef.child(userUid).addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot){
                //Log.d("MenuReview: ", "onDataChage Success")
                if (snapshot.exists()){

                    for (childSnapshot in snapshot.children){
                        val menuname = childSnapshot.child("menuName").getValue(String::class.java)
                        val menuprice = childSnapshot.child("menuPrice").getValue(String::class.java)
                        val menucount = childSnapshot.child("menuCount").getValue(Int::class.java)
                        Log.d("CartActivity", "$menuname, $menuprice, $menucount")

                        adapter.itemList.add(MenuClass((menuname ?:""), (menuprice ?: ""), ((menucount ?: "") as Int)))

                    }
                    //adapter.notifyDataSetChanged()
                }
                adapter.notifyDataSetChanged()
            }


            override fun onCancelled(error: DatabaseError) {
                Log.e("test", "loadItem:onCancelled : ${error.toException()}")
            }

        })
        BT_order.setText("메뉴 주문하기")


        // ~원 주문하기 버튼이 눌리면
        BT_order.setOnClickListener {
            /*
            var TOTALPRICE = 0
            var TOTALCOUNT = 0
            cartRef.child(userUid).addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot){
                    Log.d("MenuReview: ", "onDataChage Success")
                    if (snapshot.exists()){
                        Log.d("MenuReview: ", "Snapshot exist")
                        for (childSnapshot in snapshot.children){
                            val menuname = childSnapshot.child("menuName").getValue(String::class.java)
                            val menuprice = childSnapshot.child("menuPrice").getValue(String::class.java)
                            val menucount = childSnapshot.child("menuCount").getValue(Int::class.java)

                            TOTALPRICE += ((menuprice?.toInt()!!) * menucount!!)
                            TOTALCOUNT += menucount
                            //intent.putExtra("TOTALPRICE", TOTALPRICE)
                            //intent.putExtra("TOTALCOUNT", "$TOTALCOUNT")
                        }
                        //TOTALPRICE.toString()
                        Log.d("CartActivity Button PRICE", "$TOTALPRICE")
                        Log.d("CartActivity Button COUNT", "$TOTALCOUNT")

                        //intent.putExtra("TOTALPRICE", TOTALPRICE)
                        //intent.putExtra("TOTALCOUNT", "$TOTALCOUNT")
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.e("test", "loadItem:onCancelled : ${error.toException()}")
                }
            })
            Log.d("CartActivity End PRICE", "$TOTALPRICE")
            //Log.d("CartActivity Button COUNT", "$TOTALCOUNT")
            intent.putExtra("TOTALPRICE", TOTALPRICE)
            intent.putExtra("TOTALCOUNT", TOTALCOUNT)
            intent.putExtra("STRING", "striing")
             */

            startActivity(intent)
        }

    }
}