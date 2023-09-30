package kr.ac.duksung.minifrontapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.fragment_home.*

class AllTodayMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)

        viewAllButton.setOnClickListener {
            val intent = Intent(this, TodayMenu::class.java)
            startActivity(intent)
        }
    }
}