package kr.ac.duksung.minifrontapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var btLogin: Button
    private lateinit var btRegister: Button
    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private val db = FirebaseFirestore.getInstance()

    private val realdb = Firebase.database("https://testlogin2-a82d6-default-rtdb.firebaseio.com/")
    private val categoriesRef = realdb.getReference("MenuName")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


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
 /*       data class review(
            val reviewText: String

        )

        data class Menu(
            val price: String,
            val reviews: MutableList<String>
        )
*/
        data class TodayMenu(
            val TodayName: String,
            val price: String
        )

        data class TodayDate(
            val menuA: TodayMenu,
            val menuB: TodayMenu
        )

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

        val menu1A = TodayMenu("생야채비빔밥\n" +
                "연두부*양념장\n" +
                "서문시장st매콤어묵\n" +
                "배추김치\n" +
                "유부장국", "6000")
        val menu1B = TodayMenu("돼지고기감자짜글이\n" +
                "탕평채\n" +
                "깻잎무침\n" +
                "배추김치\n" +
                "흑미밥", "6000")
        val TodayDate1 = TodayDate(menu1A, menu1B)

        val menu2A = TodayMenu("쭈삼불고기덮밥\n" +
                "어묵잡채\n" +
                "파인애플샐러드\n" +
                "배추김치\n" +
                "파송송계란국", "6000")
        val menu2B = TodayMenu("연남동st라구소바\n" +
                "모듬감자튀김\n" +
                "상큼두부카프레제\n" +
                "배추김치\n" +
                "추가밥", "6000")
        val TodayDate2 = TodayDate(menu2A, menu2B)

        val menu3A = TodayMenu("눈꽃함박스테이크\n" +
                "계란볶음밥\n" +
                "단호박*감자샐러드\n" +
                "배추김치\n" +
                "우동국", "6000")
        val menu3B = TodayMenu("안동찜닭\n" +
                "콩나물비빔라면\n" +
                "연근조림\n" +
                "배추김치\n" +
                "흑미밥", "6000")
        val TodayDate3 = TodayDate(menu3A, menu3B)

        val menu4A = TodayMenu("해물짜장면\n" +
                "김치피자탕수육\n" +
                "단무지\n" +
                "배추김치\n" +
                "짬뽕국", "6000")
        val menu4B = TodayMenu("돈까스김치나베\n" +
                "해물동그랑땡\n" +
                "아이스망고샐러드\n" +
                "깍두기\n" +
                "흑미밥", "6000")
        val TodayDate4 = TodayDate(menu4A, menu4B)

        val menu5A = TodayMenu("참치마요밥버거\n" +
                "로제마라떡볶이\n" +
                "츄러스\n" +
                "단무지\n" +
                "어묵국", "6000")
        val menu5B = TodayMenu("소고기장터국밥\n" +
                "사각어묵볶음\n" +
                "숙주부추무침\n" +
                "석박지\n" +
                "흑미밥", "6000")
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
        categoriesRef.child("오늘의 메뉴").child("2023-10-02").setValue(TodayDate1)
        categoriesRef.child("오늘의 메뉴").child("2023-10-03").setValue(TodayDate2)
        categoriesRef.child("오늘의 메뉴").child("2023-10-04").setValue(TodayDate3)
        categoriesRef.child("오늘의 메뉴").child("2023-10-05").setValue(TodayDate4)
        categoriesRef.child("오늘의 메뉴").child("2023-10-09").setValue(TodayDate5)

    }
    private fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // 회원가입 성공
                    Toast.makeText(this, "회원가입 성공!", Toast.LENGTH_LONG).show()
                    // 필요한 작업 수행 (예: 메인 화면으로 이동)
                    // goToMainActivity(task.result?.user)auth.currentUser?.uid.toString()

                   /* val user = hashMapOf( "username" to emailId)
                    db.collection("users")
                        .document(auth.currentUser?.uid.toString())
                        .set(user)
*/
                   // val emailId = email.substringBefore('@')
                   /* val FireuserID = hashMapOf( "userID" to auth.currentUser?.uid.toString(),
                        "cart" to hashMapOf<String, Any>()
                        )*/
                    val emailId = email.substringBefore('@')

                    val FireuserID = hashMapOf( "userID" to auth.currentUser?.uid.toString())
                    db.collection("users")
                        .document(emailId)
                        .set(FireuserID)


                    val intent = Intent(this, MyPage::class.java)
                    intent.putExtra("email", emailId)
                    startActivity(intent)



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
