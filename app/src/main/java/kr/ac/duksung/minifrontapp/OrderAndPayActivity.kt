package kr.ac.duksung.minifrontapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_card.*
import kotlinx.android.synthetic.main.activity_order_and_pay.*
import kotlinx.android.synthetic.main.activity_order_and_pay.back_icon
import kotlinx.android.synthetic.main.activity_order_and_pay.bottomNavigationView
import kr.ac.duksung.minifrontapp.databinding.ActivityOrderAndPayBinding
import kr.ac.duksung.minifrontapp.databinding.SmartPayContentBinding
import java.text.DecimalFormat

class OrderAndPayActivity : AppCompatActivity() {

    private val db = Firebase.database("https://testlogin2-a82d6-default-rtdb.firebaseio.com/")
    private val cartRef = db.getReference("Cart")

    private lateinit var auth: FirebaseAuth

    // 뷰 홀더에 넣을 가변리스트(추가와 삭제가 자유로움)
    var friendsList : MutableList<FriendsID> = mutableListOf(
        FriendsID("20210691안가은"),                   // 일단 서버연동 전까지 이렇게 해둠
        FriendsID("20210692문나연")
    )

    // OrderAndPayActivity 객체화(다른 클래스에서도 참조할 수 있도록)
    init{
        var instance = this
    }

    companion object{
        private var instance:OrderAndPayActivity? = null
        fun getInstance(): OrderAndPayActivity? {
            return instance
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bindingMain = ActivityOrderAndPayBinding.inflate(layoutInflater)        // 현재 페이지와
        val bindingSmartPay = SmartPayContentBinding.inflate(layoutInflater)        // 간편결제 페이지
        setContentView(bindingMain.root)


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
                    startActivity(Intent(this@OrderAndPayActivity, HomeFragment::class.java))
                    true
                }
                R.id.page_fv -> {
                    // 친구 추가 아이템 클릭 시 친구 추가 화면으로 이동
                    startActivity(Intent(this@OrderAndPayActivity, FriendsList::class.java))
                    true
                }
                R.id.page_ps -> {
                    // 마이페이지 아이템 클릭 시 마이페이지 화면으로 이동
                    startActivity(Intent(this@OrderAndPayActivity, MyPage::class.java))
                    true
                }
                else -> false
            }
        }


        val adapter = FriendsRowAdapter(friendsList)        // 분할결제를 위한 친구목록 띄우기용 adapter
        RCV_pay.adapter = adapter                           // 이렇게 하면 항상 리사이클러뷰가 보이니까
        RCV_pay.visibility = View.INVISIBLE                 // visibility = INVISIBLE로 일단 안보이게 처리

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val userUid = currentUser!!.uid

        /*
        val intent : Intent = intent
        val TotalPrice : Int = intent.getIntExtra("TOTALPRICE", 0)
        val test: String? = intent.getStringExtra("STRING")
        Log.d("intent.getIntExtra", "$TotalPrice")
        Log.d("intent.getIntExtra", "$test")
        val TotalCount : Int = intent.getIntExtra("TOTALCOUNT", 0)
         */

        var TOTALPRICE = 0
        var TOTALCOUNT = 0

        cartRef.child(userUid).addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot){
                Log.d("MenuReview: ", "onDataChage Success")
                if (snapshot.exists()){
                    Log.d("MenuReview: ", "Snapshot exist")
                    for (childSnapshot in snapshot.children){
                        val menuprice = childSnapshot.child("menuPrice").getValue(String::class.java)
                        val menucount = childSnapshot.child("menuCount").getValue(Int::class.java)

                        TOTALPRICE += ((menuprice?.toInt()!!) * menucount!!)
                        TOTALCOUNT += menucount
                    }
                    //Log.d("CartActivity Button PRICE", "$TOTALPRICE")
                    //Log.d("CartActivity Button COUNT", "$TOTALCOUNT")
                    Order_sum_text.setText("$TOTALCOUNT 개")         // 총 주문수량 표시
                    val t_dec_up = DecimalFormat("#,###")            // 3자리씩 쉼표 넣어 표시하기 위함
                    val print_order_total = t_dec_up.format(TOTALPRICE)
                    Order_total_text.setText("$print_order_total 원")       // 총 금액 표시
                    BT_pay.setText("$print_order_total 원")
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("test", "loadItem:onCancelled : ${error.toException()}")
            }
        })
        //Log.d("CartActivity final PRICE", "$TOTALPRICE")
        //Log.d("CartActivity fianl COUNT", "$TOTALCOUNT")
        //val t_dec_up = DecimalFormat("#,###")            // 3자리씩 쉼표 넣어 표시하기 위함
        //val print_order_total = t_dec_up.format(TOTALPRICE)
        //Order_total_text.setText("$print_order_total 원")       // 총 금액 표시



        // 간편결제 버튼 눌리면
        RBT_smartpay.setOnClickListener{
            RBT_smartpay.setTextColor(Color.parseColor("#FFFFFFFF"))
            RBT_Npay.setTextColor(Color.parseColor("#FF000000"))

            SV_pay.visibility = View.VISIBLE                    // 스크롤뷰(결제방법) 보이게
            RCV_pay.visibility = View.INVISIBLE                 // 리사이클러뷰(함께 결제할 친구목록) 안 보이게
            bindingMain.SVPay.removeAllViews()                  // 이미 존재하는 자식뷰를 모두 제외(스크롤뷰는 하나의 자식뷰만 가질 수 있으므로)
            bindingMain.SVPay.addView(bindingSmartPay.root)

        }

        RBT_Npay.setOnClickListener{
            RBT_smartpay.setTextColor(Color.parseColor("#FF000000"))
            RBT_Npay.setTextColor(Color.parseColor("#FFFFFFFF"))

            SV_pay.visibility = View.INVISIBLE                   // 스크롤뷰(결제방법) 안 보이게
            RCV_pay.visibility = View.VISIBLE                    // 리사이클러뷰(함께 결제할 친구목록) 보이게
            bindingMain.SVPay.removeAllViews()
        }

        var Pay_total: Int ?= null      // 결제하기 버튼에 표시될 금액
        BT_pay.setText("$Pay_total 원 결제하기")

        // ~원 결제하기 버튼이 눌리면
        BT_pay.setOnClickListener {
            val intent = Intent(this, WaitingNumActivity::class.java) // Intent는 화면전환 담당객체. Intent(a, b)이면 a에서 b로 화면을 전환.
            startActivity(intent)
        }




    }
}