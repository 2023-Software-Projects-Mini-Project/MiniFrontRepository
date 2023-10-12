package kr.ac.duksung.minifrontapp

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_home.*
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val database = FirebaseDatabase.getInstance()
    private val categoriesRef = database.getReference("MenuName")
    private val cartRef = database.getReference("Cart")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)


        // 장바구니 아이콘 클릭시
        cart_icon.setOnClickListener{
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }


        // "View All >" 텍스트뷰를 찾습니다.
        val viewAllButton = findViewById<TextView>(R.id.viewAllButton)

        // 클릭 이벤트 리스너를 설정합니다.
        viewAllButton.setOnClickListener(View.OnClickListener { view: View? ->
            // 이동할 액티비티를 지정하는 Intent를 생성합니다.
            val intent = Intent(this@HomeFragment, TodayMenu::class.java)

            // Intent를 사용하여 액티비티를 시작합니다.
            startActivity(intent)
        })



        val textView: TextView = findViewById(R.id.today_date) // R.id.today_date는 텍스트뷰의 ID입니다.

        // 현재 날짜를 가져오고 원하는 형식으로 포맷합니다.
        val currentDate = SimpleDateFormat("yyyy.MM.dd").format(Date())

        // 텍스트뷰에 현재 날짜를 설정합니다.
        textView.text = currentDate


        val card1 = findViewById<CardView>(R.id.card1)
        val card2 = findViewById<CardView>(R.id.card2)
        val card3 = findViewById<CardView>(R.id.card3)

        card1.setOnClickListener {
            val intent = Intent(this@HomeFragment, KFoodActivity::class.java)
            startActivity(intent)
        }

        card2.setOnClickListener {
            val intent = Intent(this@HomeFragment, CFoodActivity::class.java)
            startActivity(intent)
        }

        card3.setOnClickListener {
            val intent = Intent(this@HomeFragment, BFoodActivity::class.java)
            startActivity(intent)
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        // 바텀 네비게이션 아이템 클릭 리스너 설정
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.page_fv -> {
                    // 친구 추가 아이템 클릭 시 친구 추가 화면으로 이동
                    startActivity(Intent(this@HomeFragment, FriendsList::class.java))
                    true
                }
                R.id.page_ps -> {
                    // 마이페이지 아이템 클릭 시 마이페이지 화면으로 이동
                    startActivity(Intent(this@HomeFragment, MyPage::class.java))
                    true
                }
                else -> false
            }
        }

        val todayDate = getTodayDate()
        categoriesRef.child("오늘의 메뉴").child(todayDate).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val TodayATextView = findViewById<TextView>(R.id.mon_A)
                        val TodayBTextView = findViewById<TextView>(R.id.mon_B)

                        TodayATextView.text = dataSnapshot.child("menuA").child("todayName").getValue(String::class.java)
                        TodayBTextView.text = dataSnapshot.child("menuB").child("todayName").getValue(String::class.java)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // 필요한 대로 onCancelled를 처리합니다.
                }
        })

        val cartAImageView = findViewById<ImageView>(R.id.cart_A)
        val cartBImageView = findViewById<ImageView>(R.id.cart_B)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val userUid = currentUser?.uid


        var exist : Boolean = false
        cartAImageView.setOnClickListener {
            val menuNameText = "오늘의메뉴A"
            val menuPriceText = "6000"

            if (menuNameText != null && menuPriceText != null && userUid != null) {
                Log.e("MeneReviewActivity: ", "$menuNameText, $menuPriceText, $userUid")

                // 장바구니 순회해해서 같은 아이템이 있는지 봄
                cartRef.child(userUid).addListenerForSingleValueEvent(object: ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()){

                            var oldcount : Int

                            // userid 아래 장바구니에 담아둔 메뉴 순회 시작
                            for (childSnapshot in snapshot.children){
                                var oldmenu = childSnapshot.child("menuName").getValue(String::class.java).toString()

                                if (menuNameText == oldmenu){        // 담으려는 메뉴가 장바구니에 있으면
                                    Log.e("MeneReviewActivity: ", "장바구니에 추가 안한다")
                                    exist = true

                                    oldcount = childSnapshot.child("menuCount").getValue(Int::class.java)!! // 담으려는 메뉴의 기존 수량 가져옴
                                    oldcount += 1       // 기존 수량 +1

                                    var key = childSnapshot.key

                                    val updateCount: HashMap<String, Any> = HashMap()       // updateChildren은 인스턴스로 해쉬맵만 받음
                                    updateCount["menuCount"] = oldcount                     // key: menuCount, value: oldcount로 updateCount에 저장

                                    // 루트 노드부터 타고 내려오면서 변화된 수량을 DB에 저장
                                    cartRef.child(userUid).child("$key").updateChildren(updateCount)
                                    Toast.makeText(this@HomeFragment, "$menuNameText 1인분 추가", Toast.LENGTH_SHORT).show()

                                    break
                                }

                                else{                   // 담으려는 메뉴가 장바구니에 없으면
                                    exist = false
                                }
                            }

                            if(exist == false){
                                MenuReviewActivity().addToCartTest(userUid, menuNameText, menuPriceText, 1)
                                Toast.makeText(this@HomeFragment, "$menuNameText 1인분 추가", Toast.LENGTH_SHORT).show()
                                Log.e("MeneReviewActivity: ", "장바구니에 추가한다")
                            }
                        }

                        else{
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        Log.e("test", "loadItem:onCancelled : ${error.toException()}")
                    }
                })
            }
        }

        cartBImageView.setOnClickListener {
            val menuNameText = "오늘의메뉴B"
            val menuPriceText = "6000"

            if (menuNameText != null && menuPriceText != null && userUid != null) {
                Log.e("MeneReviewActivity: ", "$menuNameText, $menuPriceText, $userUid")

                // 장바구니 순회해해서 같은 아이템이 있는지 봄
                cartRef.child(userUid).addListenerForSingleValueEvent(object: ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()){

                            var oldcount : Int

                            // userid 아래 장바구니에 담아둔 메뉴 순회 시작
                            for (childSnapshot in snapshot.children){
                                var oldmenu = childSnapshot.child("menuName").getValue(String::class.java).toString()

                                if (menuNameText == oldmenu){        // 담으려는 메뉴가 장바구니에 있으면
                                    Log.e("MeneReviewActivity: ", "장바구니에 추가 안한다")
                                    exist = true

                                    oldcount = childSnapshot.child("menuCount").getValue(Int::class.java)!! // 담으려는 메뉴의 기존 수량 가져옴
                                    oldcount += 1       // 기존 수량 +1

                                    var key = childSnapshot.key

                                    val updateCount: HashMap<String, Any> = HashMap()       // updateChildren은 인스턴스로 해쉬맵만 받음
                                    updateCount["menuCount"] = oldcount                     // key: menuCount, value: oldcount로 updateCount에 저장

                                    // 루트 노드부터 타고 내려오면서 변화된 수량을 DB에 저장
                                    cartRef.child(userUid).child("$key").updateChildren(updateCount)
                                    Toast.makeText(this@HomeFragment, "$menuNameText 1인분 추가", Toast.LENGTH_SHORT).show()

                                    break
                                }

                                else{                   // 담으려는 메뉴가 장바구니에 없으면
                                    exist = false
                                    Log.e("MeneReviewActivity: ", "장바구니에 추가한다")
                                }
                            }

                            if(exist == false){
                                MenuReviewActivity().addToCartTest(userUid, menuNameText, menuPriceText, 1)
                                Toast.makeText(this@HomeFragment, "$menuNameText 1인분 추가", Toast.LENGTH_SHORT).show()
                                Log.e("MeneReviewActivity: ", "장바구니에 추가한다")
                            }
                        }

                    }
                    override fun onCancelled(error: DatabaseError) {
                        Log.e("test", "loadItem:onCancelled : ${error.toException()}")
                    }
                })
            }
        }
    }

    private fun getTodayDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }
}