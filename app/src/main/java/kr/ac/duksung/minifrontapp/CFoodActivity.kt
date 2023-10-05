package kr.ac.duksung.minifrontapp

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

class CFoodActivity : AppCompatActivity() {

    private val db = Firebase.database("https://testlogin2-a82d6-default-rtdb.firebaseio.com/")
    private val categoriesRef = db.getReference("category")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cfood_list)

        val backIcon = findViewById<ImageView>(R.id.back_icon)

        backIcon.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish() // 현재 액티비티 종료
            }
        })

        categoriesRef.child("중식").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {

                    /*val jajangmyeonData = dataSnapshot.child("0")


                    val jajangmyeonName = jajangmyeonData.child("menuName").getValue(String::class.java)
                    val jajangmyeonPrice = jajangmyeonData.child("price").getValue(String::class.java)


                    val jajangmyeonTextView = findViewById<TextView>(R.id.jajangmyeon_text)
                    val jajangmyeonPriceTextView = findViewById<TextView>(R.id.jajangmyeon_price)

                    jajangmyeonTextView.text = jajangmyeonName
                    jajangmyeonPriceTextView.text = jajangmyeonPrice
*/

                    val jajangmyeonTextView = findViewById<TextView>(R.id.jajangmyeon_text)
                    val jajangmyeonPriceTextView = findViewById<TextView>(R.id.jajangmyeon_price)

                    jajangmyeonTextView.text = dataSnapshot.child("0").child("menuName").getValue(String::class.java)
                    jajangmyeonPriceTextView.text = dataSnapshot.child("0").child("price").getValue(String::class.java)
                }

                if (dataSnapshot.exists()) {

                    val jjamppongData = dataSnapshot.child("1")


                    val jjamppongName = jjamppongData.child("menuName").getValue(String::class.java)
                    val jjamppongPrice = jjamppongData.child("price").getValue(String::class.java)


                    val jjamppongTextView = findViewById<TextView>(R.id.jjamppong_text)
                    val jjamppongPriceTextView = findViewById<TextView>(R.id.jjamppong_price)

                    jjamppongTextView.text = jjamppongName
                    jjamppongPriceTextView.text = jjamppongPrice
                }

                if (dataSnapshot.exists()) {

                    val tangsuyukData = dataSnapshot.child("2")


                    val tangsuyukName = tangsuyukData.child("menuName").getValue(String::class.java)
                    val tangsuyukPrice = tangsuyukData.child("price").getValue(String::class.java)


                    val tangsuyukTextView = findViewById<TextView>(R.id.tangsuyuk_text)
                    val tangsuyukPriceTextView = findViewById<TextView>(R.id.tangsuyuk_price)

                    tangsuyukTextView.text = tangsuyukName
                    tangsuyukPriceTextView.text = tangsuyukPrice
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 데이터 읽기가 취소된 경우
                Log.e("Firebase", "Data read cancelled: ${databaseError.message}")
            }
        })
    }
}