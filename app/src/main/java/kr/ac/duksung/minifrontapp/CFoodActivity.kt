package kr.ac.duksung.minifrontapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.cfood_list.*
import kotlinx.android.synthetic.main.cfood_list.back_icon
import kotlinx.android.synthetic.main.kfood_list.*

class CFoodActivity : AppCompatActivity() {

    private val db = Firebase.database("https://testlogin2-a82d6-default-rtdb.firebaseio.com/")
    private val menunameRef = db.getReference("MenuName")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cfood_list)

        back_icon.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish() // 현재 액티비티 종료
            }
        })

        goJajangmyeon.setOnClickListener {
            val intent = Intent(this@CFoodActivity, MenuReviewActivity::class.java)
            intent.putExtra("menuName", "짜장면")
            startActivity(intent)
        }

        goJjamppong.setOnClickListener{
            val intent = Intent(this@CFoodActivity, MenuReviewActivity::class.java)
            intent.putExtra("menuName", "짬뽕")
            startActivity(intent)
        }

        goTangsuyuk.setOnClickListener{
            val intent = Intent(this@CFoodActivity, MenuReviewActivity::class.java)
            intent.putExtra("menuName", "탕수육")
            startActivity(intent)
        }



    }
}