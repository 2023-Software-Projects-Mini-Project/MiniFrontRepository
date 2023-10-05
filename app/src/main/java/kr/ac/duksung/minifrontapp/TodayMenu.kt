package kr.ac.duksung.minifrontapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*


class TodayMenu : AppCompatActivity() {

    private val database = FirebaseDatabase.getInstance()
    private val categoriesRef = database.getReference("MenuName")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_today_menu)

        val backIcon = findViewById<ImageView>(R.id.back_icon)

        backIcon.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish() // 현재 액티비티 종료
            }
        })


      //  val currentDate = getCurrentDate()


        categoriesRef.child("오늘의 메뉴").child("2023-10-02").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val monATextView = findViewById<TextView>(R.id.mon_A)
                val monBTextView = findViewById<TextView>(R.id.mon_B)
                // val tueATextView = findViewById<TextView>(R.id.tue_A)
                // val tueBTextView = findViewById<TextView>(R.id.tue_B)

                monATextView.text = dataSnapshot.child("menuA").child("todayName").getValue(String::class.java)
                monBTextView.text = dataSnapshot.child("menuB").child("todayName").getValue(String::class.java)
                //tueATextView.text = dataSnapshot.child("menuA").child("menuName").getValue(String::class.java)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 필요한 대로 onCancelled를 처리합니다.
            }
        })
        categoriesRef.child("오늘의 메뉴").child("2023-10-03").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val tueATextView = findViewById<TextView>(R.id.tue_A)
                val tueBTextView = findViewById<TextView>(R.id.tue_B)

                tueATextView.text = dataSnapshot.child("menuA").child("todayName").getValue(String::class.java)
                tueBTextView.text = dataSnapshot.child("menuB").child("todayName").getValue(String::class.java)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 필요한 대로 onCancelled를 처리합니다.
            }
        })
        categoriesRef.child("오늘의 메뉴").child("2023-10-04").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val wenATextView = findViewById<TextView>(R.id.wen_A)
                val wenBTextView = findViewById<TextView>(R.id.wen_B)

                wenATextView.text = dataSnapshot.child("menuA").child("todayName").getValue(String::class.java)
                wenBTextView.text = dataSnapshot.child("menuB").child("todayName").getValue(String::class.java)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 필요한 대로 onCancelled를 처리합니다.
            }
        })
        categoriesRef.child("오늘의 메뉴").child("2023-10-05").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val thuATextView = findViewById<TextView>(R.id.thu_A)
                val thuBTextView = findViewById<TextView>(R.id.thu_B)

                thuATextView.text = dataSnapshot.child("menuA").child("todayName").getValue(String::class.java)
                thuBTextView.text = dataSnapshot.child("menuB").child("todayName").getValue(String::class.java)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 필요한 대로 onCancelled를 처리합니다.
            }
        })
        categoriesRef.child("오늘의 메뉴").child("2023-10-06").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val friATextView = findViewById<TextView>(R.id.fri_A)
                val friBTextView = findViewById<TextView>(R.id.fri_B)

                friATextView.text = dataSnapshot.child("menuA").child("todayName").getValue(String::class.java)
                friBTextView.text = dataSnapshot.child("menuB").child("todayName").getValue(String::class.java)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 필요한 대로 onCancelled를 처리합니다.
            }
        })





    }
    // "yyyy-MM-dd" 형식으로 현재 날짜를 가져오는 함수
    /*
    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }

     */
}



