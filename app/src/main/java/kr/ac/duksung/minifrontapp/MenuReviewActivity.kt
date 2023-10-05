package kr.ac.duksung.minifrontapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.menu_review.*

class MenuReviewActivity : AppCompatActivity() {

    private val db = Firebase.database("https://testlogin2-a82d6-default-rtdb.firebaseio.com/")
    val menunameRef = db.getReference("MenuName")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_review)

        val backIcon = findViewById<ImageView>(R.id.back_icon)
        backIcon.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish() // 현재 액티비티 종료
            }
        })

        var reviewList : MutableList<ReviewClass> = mutableListOf(
            ReviewClass(4.5f, "맛있어요!"), // 일단 서버연동 전까지 이렇게 해둠
            ReviewClass(5.0f, "맛있어요!"),
        )


        val intent : Intent = intent
        val menuNameText = intent.getStringExtra("menuName")
        menu_name.setText(menuNameText)

/*
        menunameRef.child(intent.getStringExtra("김밥").toString()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {

                    reviewList.add(
                        ReviewClass(
                        4.5f, dataSnapshot.child("reviews").getValue(String::class.java).toString()
                    ))

                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 데이터 읽기가 취소된 경우
                Log.e("Firebase", "Data read cancelled: ${databaseError.message}")
            }
        })

 */

        val adapter = MenuReviewAddAdapter(reviewList)
        menu_review.adapter = adapter

    }
}
