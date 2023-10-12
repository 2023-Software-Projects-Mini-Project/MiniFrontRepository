package kr.ac.duksung.minifrontapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var btLogin: Button
    private lateinit var btRegister: Button
    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private val db = FirebaseFirestore.getInstance()

    //data class Usersinfo(val username: String, val usertoken: String, val userid : String)

    private val realdb = Firebase.database("https://testlogin2-a82d6-default-rtdb.firebaseio.com/")
    private val categoriesRef = realdb.getReference("MenuName")
    private val cartRef = realdb.getReference("Cart")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // FCM 토큰 가져오기
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                Log.d("FCM Token", token)
            } else {
                Log.e("FCM Token", "Failed to get token")
            }
        }


        auth = FirebaseAuth.getInstance()

        btLogin = findViewById(R.id.bt_login)
        btRegister = findViewById(R.id.bt_register)
        editEmail = findViewById(R.id.edit_email)
        editPassword = findViewById(R.id.edit_password)

        btLogin.setOnClickListener {
            val email = editEmail.text.toString().trim()
            val password = editPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                signIn(email, password)
            } else {
                Toast.makeText(this, "이메일과 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show()
            }
        }

        btRegister.setOnClickListener {
            val email = editEmail.text.toString().trim()
            val password = editPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                signUp(email, password)
            } else {
                Toast.makeText(this, "이메일과 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show()
            }
        }
        data class review(
            val reviewText: String

        )

        data class Menu(
            val price: String,
            val reviews: MutableList<String>
        )

        data class TodayMenu(
            val TodayName: String,
            val price: String
        )

        data class TodayDate(
            val menuA: TodayMenu,
            val menuB: TodayMenu
        )
//주석 해제하지 마세용
        /*       val kfood1 = Menu("5500", mutableListOf("맛나요"))
               val kfood2 = Menu("5500", mutableListOf("맛나요"))
               val kfood3 = Menu("5500", mutableListOf("맛나요"))
               // 다른 메뉴 추가
               val cfood1 = Menu("6000", mutableListOf("맛나요"))
               val cfood2 = Menu("6000", mutableListOf("맛나요"))
               val cfood3 = Menu("4000", mutableListOf("맛나요"))

               val boonsik1 = Menu("3000", mutableListOf("맛나요"))
               val boonsik2 = Menu("4000", mutableListOf("맛나요"))
               val boonsik3 = Menu("5000", mutableListOf("맛나요"))
       */
/*

        val menu1A = TodayMenu("대체휴무", "6000")
        val menu1B = TodayMenu("대체휴무", "6000")
        val TodayDate1 = TodayDate(menu1A, menu1B)

        val menu2A = TodayMenu("개천절", "6000")
        val menu2B = TodayMenu("개천절", "6000")
        val TodayDate2 = TodayDate(menu2A, menu2B)

        val menu3A = TodayMenu("훈제오리볶음밥\n" +
                "생선까스*타르타르\n" +
                "나쵸샐러드\n" +
                "깍두기\n" +
                "우동국", "6000")
        val menu3B = TodayMenu("짬뽕순두부\n" +
                "메추리알장조림\n" +
                "고구마맛탕\n" +
                "깍두기\n" +
                "흑미밥", "6000")
        val TodayDate3 = TodayDate(menu3A, menu3B)

        val menu4A = TodayMenu("수제치킨햄버거\n" +
                "모듬감자튀김*케찹\n" +
                "파르팔레샐러드\n" +
                "할라피뇨*피클\n" +
                "콜라", "6000")
        val menu4B = TodayMenu("태국식등뼈탕\n" +
                "새우까스*콘소스\n" +
                "무쏨땀\n" +
                "배추김치\n" +
                "추가밥", "6000")
        val TodayDate4 = TodayDate(menu4A, menu4B)

        val menu5A = TodayMenu("현풍st딝칼국수\n" +
                "바삭떡만순강정\n" +
                "숙주나물\n" +
                "석박지\n" +
                "추가밥", "6000")
        val menu5B = TodayMenu("비빔막국수\n" +
                "콩나물국\n" +
                "메밀전병*부추무침\n" +
                "리코타베리믹스샐러드\n" +
                "열무김치\n", "6000")
        val TodayDate5 = TodayDate(menu5A, menu5B)
/*
        categoriesRef.child("김치찌개").setValue(kfood1)
        categoriesRef.child("된장찌개").setValue(kfood2)
        categoriesRef.child("부대찌개").setValue(kfood3)

        categoriesRef.child("짜장면").setValue(cfood1)
        categoriesRef.child("짬뽕").setValue(cfood2)
        categoriesRef.child("탕수육").setValue(cfood3)

        categoriesRef.child("김밥").setValue(boonsik1)
        categoriesRef.child("라면").setValue(boonsik2)
        categoriesRef.child("떡볶이").setValue(boonsik3)
*/
        categoriesRef.child("오늘의 메뉴").child("2023-10-09").setValue(TodayDate1)
        categoriesRef.child("오늘의 메뉴").child("2023-10-10").setValue(TodayDate2)
        categoriesRef.child("오늘의 메뉴").child("2023-10-11").setValue(TodayDate3)
        categoriesRef.child("오늘의 메뉴").child("2023-10-12").setValue(TodayDate4)
        categoriesRef.child("오늘의 메뉴").child("2023-10-13").setValue(TodayDate5)
*/
    }
    data class Usersinfo(val username: String,val friends: List<String> ,val userid : String)
    private fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // 회원가입 성공
                    Toast.makeText(this, "회원가입 성공!", Toast.LENGTH_LONG).show()

                    val emailId = email.substringBefore('@')

                    val user = hashMapOf( "username" to emailId)
                    db.collection("users")
                        .document(auth.currentUser?.uid.toString())
                        .set(user)

                    val intent = Intent(this, MyPage::class.java)
                    intent.putExtra("email", emailId)
                    startActivity(intent)

                    //리얼타임데이터에 넣기
                    val userId = auth.currentUser?.uid.toString()
                    val friendsList = listOf<String>()
                    val giveEmptyList: HashMap<String, Any> = HashMap()
                    giveEmptyList[userId] = ""
                    cartRef.updateChildren(giveEmptyList)

                    val Usersinfo = Usersinfo(emailId, friendsList, userId)

                    val ref = realdb.getReference("users") // "users"는 데이터베이스의 경로입니다.

                    // 사용자 이름(username)을 키(key)로 사용하여 데이터를 저장
                    val userRef = ref.child(emailId) // emailId를 사용하여 사용자를 저장

                    userRef.setValue(Usersinfo) // Realtime Database에 추가




                    val mainIntent = Intent(this, HomeFragment::class.java)
                    startActivity(mainIntent)

                } else {
                    // 회원가입 실패
                    val errorMessage = task.exception?.message
                    if (errorMessage.isNullOrEmpty()) {
                        Toast.makeText(this, "등록에 실패했습니다.", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
                    }
                }
            }
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // 로그인에 성공한 경우 메인 화면으로 이동
                    // goToMainActivity(task.result?.user)
                    Toast.makeText(this, "로그인 성공!", Toast.LENGTH_LONG).show()
                    val mainIntent = Intent(this, HomeFragment::class.java)
                    startActivity(mainIntent)
                }
                else {
                    // 회원가입 실패
                    val errorMessage = task.exception?.message
                    if (errorMessage.isNullOrEmpty()) {
                        Toast.makeText(this, "로그인에 실패했습니다.", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
                    }
                }
            }
    }
}
