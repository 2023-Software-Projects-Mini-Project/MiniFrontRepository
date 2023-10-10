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
import com.google.firebase.messaging.FirebaseMessaging

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
        editEmail = findViewById(R.id.edit_id)
        editPassword = findViewById(R.id.edit_pw)

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
    }
    data class Usersinfo(val username: String, val usertoken: String, val userid : String)

    private fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // 회원가입 성공
                    Toast.makeText(this, "회원가입 성공!", Toast.LENGTH_LONG).show()
                    // 필요한 작업 수행 (예: 메인 화면으로 이동)
                    // goToMainActivity(task.result?.user)auth.currentUser?.uid.toString()
                    /* val emailId = email.substringBefore('@')
                     val user = hashMapOf( "username" to emailId)
                     db.collection("users")
                         .document(auth.currentUser?.uid.toString())
                         .set(user)
 */
                    val emailId = email.substringBefore('@')
                    /* val FireuserID = hashMapOf( "userID" to auth.currentUser?.uid.toString(),
                         "cart" to hashMapOf<String, Any>()
                         )*/
                    val FireuserID = hashMapOf( "userID" to auth.currentUser?.uid.toString(),
                    "friendlsit" to hashMapOf<String, Any>()
                    )
                    db.collection("users")
                        .document(emailId)
                        .set(FireuserID)

                    val mainIntent = Intent(this, HomeFragment::class.java)
                    startActivity(mainIntent)

                    //리얼타임데이터에 넣기

                    val userId = auth.currentUser?.uid.toString()
                    val userToken = "yourUserTokenHere" // 클라우드메시지 토큰

                    val Usersinfo = Usersinfo(emailId, userToken, userId)

                    val ref = realdb.getReference("users") // "users"는 데이터베이스의 경로입니다.
                    val userRef = ref.child(userId)

                    userRef.setValue(Usersinfo) //realtime database에 추가
                    val userMap = HashMap<String, Any>()
                    userMap["userID"] = userId

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

 /*   private fun initFirebase() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val tokenTextView = null
                tokenTextView.text = task.result
            }
        }
    }

 */
}