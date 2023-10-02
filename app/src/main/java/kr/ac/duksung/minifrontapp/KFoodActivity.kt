package kr.ac.duksung.minifrontapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.ImageView


class KFoodActivity : AppCompatActivity() {
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
    }
}
