package kr.ac.duksung.minifrontapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ReviewWrite : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_review)

        val items = arrayOf("김치찌개", "된장찌개", "부대찌개", "짜장면", "짬뽕", "탕수육", "김밥", "라면", "떡볶이")
    }
}