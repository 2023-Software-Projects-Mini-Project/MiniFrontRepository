package kr.ac.duksung.minifrontapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.boonski_list.*
import kotlinx.android.synthetic.main.boonski_list.back_icon

class BFoodActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.boonski_list)

        back_icon.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish() // 현재 액티비티 종료
            }
        })

        goKimbab.setOnClickListener{
            val intent = Intent(this@BFoodActivity, MenuReviewActivity::class.java)
            intent.putExtra("menuName", "김밥")
            startActivity(intent)
        }

        goRamen.setOnClickListener{
            val intent = Intent(this@BFoodActivity, MenuReviewActivity::class.java)
            intent.putExtra("menuName", "라면")
            startActivity(intent)
        }

        goTtokppokki.setOnClickListener{
            val intent = Intent(this@BFoodActivity, MenuReviewActivity::class.java)
            intent.putExtra("menuName", "떡볶이")
            startActivity(intent)
        }
    }
}