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
    private val categoriesRef = database.getReference("category")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_today_menu)

        val backIcon = findViewById<ImageView>(R.id.back_icon)

        backIcon.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish() // 현재 액티비티 종료
            }
        })

        val dates = listOf("2023-10-04", "2023-10-05", "2023-10-06")

        for (date in dates) {
            fetchMenuForDate(date)
        }
    }
      //  val currentDate = getCurrentDate()
      fun fetchMenuForDate(date: String) {
          val dateRef = categoriesRef.child("오늘의 메뉴").child(date)
          dateRef.addListenerForSingleValueEvent(object : ValueEventListener {
              override fun onDataChange(dataSnapshot: DataSnapshot) {

                  val monATextView = findViewById<TextView>(R.id.mon_A)
                  val monBTextView = findViewById<TextView>(R.id.mon_B)
                  val tueATextView = findViewById<TextView>(R.id.tue_A)

                  monATextView.text = dataSnapshot.child("menuA").child("menuName").getValue(String::class.java)
                  monBTextView.text = dataSnapshot.child("menuB").child("menuName").getValue(String::class.java)
                  tueATextView.text = dataSnapshot.child("menuA").child("menuName").getValue(String::class.java)

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


