package kr.ac.duksung.minifrontapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.kfood_list.*


class KFoodActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kfood_list)

        goKimchi.setOnClickListener {
            val intent = Intent(this@KFoodActivity, MenuReviewActivity::class.java)
            intent.putExtra("MenuName", "김치찌개")
            startActivity(intent)
        }

        goDoenjang.setOnClickListener{
            val intent = Intent(this@KFoodActivity, MenuReviewActivity::class.java)
            intent.putExtra("MenuName", "된장찌개")
            startActivity(intent)
        }

        goBoodea.setOnClickListener{
            val intent = Intent(this@KFoodActivity, MenuReviewActivity::class.java)
            intent.putExtra("MenuName", "부대찌개")
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
