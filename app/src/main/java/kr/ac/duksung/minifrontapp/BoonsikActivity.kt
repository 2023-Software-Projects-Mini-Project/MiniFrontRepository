package kr.ac.duksung.minifrontapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.boonski_list.*
import kotlinx.android.synthetic.main.kfood_list.*

class BoonsikActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.boonski_list)

        val backIcon = findViewById<ImageView>(R.id.back_icon)

        backIcon.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish() // 현재 액티비티 종료
            }
        })

        goKimchi.setOnClickListener{
            val intent = Intent(this@BoonsikActivity, MenuReviewActivity::class.java)
            intent.putExtra("MenuName", "김밥")
            startActivity(intent)
        }

        goRamen.setOnClickListener{
            val intent = Intent(this@BoonsikActivity, MenuReviewActivity::class.java)
            intent.putExtra("MenuName", "라면")
            startActivity(intent)
        }

        goTtokppokki.setOnClickListener{
            val intent = Intent(this@BoonsikActivity, MenuReviewActivity::class.java)
            intent.putExtra("MenuName", "떡볶이")
            startActivity(intent)
        }



    }
}