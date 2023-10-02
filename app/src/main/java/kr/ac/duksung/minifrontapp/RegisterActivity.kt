package kr.ac.duksung.minifrontapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    var Empty = false        // 빈칸이 있는지
    var PWSame = false       // 비밀번호와 비밀번호 확인이 같은지
    var id:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // 회원가입 버튼이 클릭되면
        bt_register2.setOnClickListener{
            val stuNum = edit_stuNum.text.toString().trim()
            val name = edit_name.text.toString().trim()
            val pw = edit_regipw.text.toString().trim()
            val check_pw = edit_checkpw.text.toString().trim()

            if(stuNum.isEmpty() || name.isEmpty() || pw.isEmpty() || check_pw.isEmpty()){       // 입력 안 한 부분이 있을경우
                Empty = true
            }
            else{
                if(pw == check_pw){             // 비밀번호가 같은지 확인
                    PWSame = true
                }
            }

            if(!Empty && PWSame) {              // 입력란을 모두 채우고, 비밀번호가 같을 경우
                id = stuNum+name                // id는 학번+이름이도록 (ex. 20210692문나연)해서 서버에 넘기는게 편하지 않을까?
                // 서버랑 연결되면, 아이디 중복체크도 해야함...?

                Toast.makeText(this, "회원가입 성공\n아이디는 $id 입니다", Toast.LENGTH_SHORT).show()      // 회원가입 성공 토스트 메세지 띄우기

                val intent = Intent(this, MainActivity::class.java)               // 로그인 화면으로 이동
                startActivity(intent)
            }

        }


    }
}