package kr.ac.duksung.minifrontapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.menu_review.*

class MenuReviewActivity : AppCompatActivity() {

    var reviewList : MutableList<ReviewClass> = mutableListOf(
        ReviewClass(4.5f, "맛있어요!"), // 일단 서버연동 전까지 이렇게 해둠
        ReviewClass(5.0f, "맛있어요!"),
        ReviewClass(3.5f, "맛있어요!"),
        ReviewClass(4.5f, "맛있어요!")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_review)

        val backIcon = findViewById<ImageView>(R.id.back_icon)

        backIcon.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish() // 현재 액티비티 종료
            }
        })


        val adapter = MenuReviewAddAdapter(reviewList)
        menu_review.adapter = adapter
    }
}
