package kr.ac.duksung.minifrontapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var btLogin: Button
    private lateinit var btRegister: Button
    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private val db = FirebaseFirestore.getInstance()

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
    }
    private fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // 회원가입 성공
                    Toast.makeText(this, "회원가입 성공!", Toast.LENGTH_LONG).show()
                    // 필요한 작업 수행 (예: 메인 화면으로 이동)
                    // goToMainActivity(task.result?.user)
                    val emailId = email.substringBefore('@')
                    val user = hashMapOf( "username" to emailId)
                    db.collection("users")
                        .document(auth.currentUser?.uid.toString())
                        .set(user)

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
