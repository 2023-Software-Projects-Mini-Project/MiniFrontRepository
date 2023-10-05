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


        // 서버에서 정보 받아오게
        /*
        menunameRef.child("중식").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {

                    jajangmyeon_text.text = dataSnapshot.child("0").child("menuName").getValue(String::class.java)
                    jajangmyeon_price.text = dataSnapshot.child("0").child("price").getValue(String::class.java)
                }

                if (dataSnapshot.exists()) {

                    jjamppong_text.text = dataSnapshot.child("1").child("menuName").getValue(String::class.java)
                    jjamppong_price.text = dataSnapshot.child("1").child("price").getValue(String::class.java)
                }

                if (dataSnapshot.exists()) {

                    tangsuyuk_text.text = dataSnapshot.child("2").child("menuName").getValue(String::class.java)
                    tangsuyuk_price.text = dataSnapshot.child("2").child("price").getValue(String::class.java)
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 데이터 읽기가 취소된 경우
                Log.e("Firebase", "Data read cancelled: ${databaseError.message}")
            }
        })

         */

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