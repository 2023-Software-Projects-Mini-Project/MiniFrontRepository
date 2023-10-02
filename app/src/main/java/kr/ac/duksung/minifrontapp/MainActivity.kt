package kr.ac.duksung.minifrontapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*       // 버튼을 객체 등록하지 않고 버튼의 고유 아이디로 바로 불러오려면 import 해야함
// Gradle Scripts에 plugins에 id 'kotlin-android-extensions'을 등록하고 sync 해줘야함


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 로그인 버튼이 클릭되면
        bt_login.setOnClickListener {
            var id = edit_id.text.toString().trim()     // editText로부터 입력된 값 받아오기(공백제거)
            var pw = edit_pw.text.toString().trim()

            if(id == "0000" && pw == "0000"){           // 아이디와 비번이 맞으면(일단은 아이디 비번 0000으로 하고 앱 돌아가게 하려고. 나중에 서버에서 받아오는걸로!)
                val intent = Intent(this, CartActivity::class.java)     // 장바구니 페이지로 넘어감
                startActivity(intent)
            }
            else if(id != "0000")                       // 아이디가 틀렸을 때
                Toast.makeText(this, "아이디를 확인해주세요", Toast.LENGTH_SHORT).show()
            else                                        // 비밀번호가 틀렸을 때
                Toast.makeText(this, "비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show()
        }

        // 회원가입 버튼이 클릭되면
        bt_register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java) // Intent는 화면전환 담당객체. Intent(a, b)이면 a에서 b로 화면을 전환.
            startActivity(intent)
        }


    }
}