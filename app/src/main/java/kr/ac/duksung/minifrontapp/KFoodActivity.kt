package kr.ac.duksung.minifrontapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class KFoodActivity : AppCompatActivity() {

    private val db = Firebase.database("https://testlogin2-a82d6-default-rtdb.firebaseio.com/")
    private val categoriesRef = db.getReference("category")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kfood_list)

        val goKimchi = findViewById<ImageView>(R.id.goKimchi)

        goKimchi.setOnClickListener {
            val intent = Intent(this@KFoodActivity, MenuReviewActivity::class.java)
            startActivity(intent)
        }

        val backIcon = findViewById<ImageView>(R.id.back_icon)

        backIcon.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish() // 현재 액티비티 종료
            }
        })

        categoriesRef.child("한식").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    // "한식" 카테고리의 데이터가 존재하는 경우
                    val kimchiData = dataSnapshot.child("0")

                    // 김치찌개의 이름과 가격 데이터 가져오기
                    val kimchiName = kimchiData.child("menuName").getValue(String::class.java)
                    val kimchiPrice = kimchiData.child("price").getValue(String::class.java)

                    // 김치찌개 데이터를 UI의 TextView에 설정
                    val kimchiTextView = findViewById<TextView>(R.id.kimchi_text)
                    val kimchiPriceTextView = findViewById<TextView>(R.id.kimchi_price)

                    kimchiTextView.text = kimchiName
                    kimchiPriceTextView.text = kimchiPrice
                }

                if (dataSnapshot.exists()) {
                    // "한식" 카테고리의 데이터가 존재하는 경우
                    val doenjangData = dataSnapshot.child("1")

                    // 김치찌개의 이름과 가격 데이터 가져오기
                    val doenjangName = doenjangData.child("menuName").getValue(String::class.java)
                    val doenjangPrice = doenjangData.child("price").getValue(String::class.java)

                    // 김치찌개 데이터를 UI의 TextView에 설정
                    val doenjangTextView = findViewById<TextView>(R.id.doenjang_text)
                    val doenjangPriceTextView = findViewById<TextView>(R.id.doenjang_price)

                    doenjangTextView.text = doenjangName
                    doenjangPriceTextView.text = doenjangPrice
                }

                if (dataSnapshot.exists()) {
                    // "한식" 카테고리의 데이터가 존재하는 경우
                    val boodaeData = dataSnapshot.child("2")

                    // 김치찌개의 이름과 가격 데이터 가져오기
                    val boodaeName = boodaeData.child("menuName").getValue(String::class.java)
                    val boodaePrice = boodaeData.child("price").getValue(String::class.java)

                    // 김치찌개 데이터를 UI의 TextView에 설정
                    val boodaeTextView = findViewById<TextView>(R.id.boodae_text)
                    val boodaePriceTextView = findViewById<TextView>(R.id.boodae_price)

                    boodaeTextView.text = boodaeName
                    boodaePriceTextView.text = boodaePrice
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 데이터 읽기가 취소된 경우
                Log.e("Firebase", "Data read cancelled: ${databaseError.message}")
            }
        })
    }
}
