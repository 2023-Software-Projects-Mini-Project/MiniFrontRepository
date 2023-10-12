package kr.ac.duksung.minifrontapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.snapshots
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
    private val totalorderRef = db.getReference("TotalOrder")

    private lateinit var auth: FirebaseAuth

    // 뷰 홀더에 넣을 가변리스트(추가와 삭제가 자유로움)
    var friendsList : MutableList<FriendsID> = mutableListOf(
        FriendsID("20210691"),                            // 일단 서버연동 전까지 이렇게 해둠
        FriendsID("20210692"),
        FriendsID("20210805"),
        FriendsID("20210900"),
        FriendsID("20211121"),
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



        var Choice : Boolean = false
        var DividePay : Boolean = false

        // 간편결제 버튼 눌리면
        RBT_smartpay.setOnClickListener{
            DividePay = false
            Choice = true
            RBT_smartpay.setTextColor(Color.parseColor("#FFFFFFFF"))
            RBT_Npay.setTextColor(Color.parseColor("#FF000000"))

            SV_pay.visibility = View.VISIBLE                    // 스크롤뷰(결제방법) 보이게
            RCV_pay.visibility = View.INVISIBLE                 // 리사이클러뷰(함께 결제할 친구목록) 안 보이게
            bindingMain.SVPay.removeAllViews()                  // 이미 존재하는 자식뷰를 모두 제외(스크롤뷰는 하나의 자식뷰만 가질 수 있으므로)
            bindingMain.SVPay.addView(bindingSmartPay.root)

        }

        // 분할결제 버튼 눌리면
        RBT_Npay.setOnClickListener{
            DividePay = true
            Choice = true
            RBT_smartpay.setTextColor(Color.parseColor("#FF000000"))
            RBT_Npay.setTextColor(Color.parseColor("#FFFFFFFF"))

            SV_pay.visibility = View.INVISIBLE                   // 스크롤뷰(결제방법) 안 보이게
            RCV_pay.visibility = View.VISIBLE                    // 리사이클러뷰(함께 결제할 친구목록) 보이게
            bindingMain.SVPay.removeAllViews()

            var Pay_total: Int ?= null      // 결제하기 버튼에 표시될 금액
            BT_pay.setText("나눠서 결제하기")
        }


        // ~원 결제하기 버튼이 눌리면
        BT_pay.setOnClickListener {
            if (Choice == true){
                if (DividePay == false) {     // 나 혼자 결제면

                    var changeDate: Boolean = true
                    var count : Int = 0
                    Log.d("OrderAndPay", "start $count")

                    totalorderRef.addValueEventListener(object: ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot){
                            count = snapshot.childrenCount.toInt()                  // 현재 레코드의 수를 가져옴
                            Log.d("OrderAndPay", "onDataChange $count")

                            if(changeDate){
                                addTotalOrder(userUid, count)                       // 주문번호 생성
                                changeDate = false
                            }
                        }
                        override fun onCancelled(error: DatabaseError) {
                            Log.e("test", "loadItem:onCancelled : ${error.toException()}")
                        }
                    })
                    Log.d("OrderAndPay", "finish $count")

                    ClearCart()     // 카트를 지움

                }
                else { // 함께 결제면



                }

            }

            else{
                Toast.makeText(this, "결제 방법을 선택해주세요.", Toast.LENGTH_SHORT).show()
            }

            //val intent = Intent(this, WaitingNumActivity::class.java) // Intent는 화면전환 담당객체. Intent(a, b)이면 a에서 b로 화면을 전환.
            //startActivity(intent)
        }

    }

    // 사용자 로그인 및 UID 얻기
    fun addTotalOrder(userUid: String, key: Int) {

        val newItem = userUid
        //totalorderRef.child("$key").setValue(userUid)
        totalorderRef.child("$key").child("OrderNum").setValue("$key")
        totalorderRef.child("$key").child("userid").setValue(userUid)
        Log.d("Cart", "Added to cart: $userUid, $key")

    }

    fun ClearCart(){
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val userUid = currentUser!!.uid

        Log.e("test", "ClearCart 함수 들어옴")

        cartRef.child(userUid).setValue("")
    }
}