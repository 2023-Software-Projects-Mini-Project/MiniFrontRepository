package kr.ac.duksung.minifrontapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_card.*


class Card : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)

        val backIcon = findViewById<ImageView>(R.id.back_icon)
        val kbDeleteButton = findViewById<View>(R.id.kb_delete)
        val shDeleteButton = findViewById<View>(R.id.sh_delete)
        val kbMainButton = findViewById<View>(R.id.kb_main)
        val shMainButton = findViewById<View>(R.id.sh_main)

        backIcon.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish() // 현재 액티비티 종료
            }
        })

        kbDeleteButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                // 국민카드 LinearLayout을 찾고 삭제
                val kbCardLayout = findViewById<LinearLayout>(R.id.layout_kb_card)
                val parentLayout = kbCardLayout.parent as? LinearLayout
                parentLayout?.removeView(kbCardLayout)
            }
        })

        shDeleteButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                // 신한카드 LinearLayout을 찾고 삭제
                val shCardLayout = findViewById<LinearLayout>(R.id.layout_sh_card)
                val parentLayout = shCardLayout.parent as? LinearLayout
                parentLayout?.removeView(shCardLayout)
            }
        })

        //주카드 설정
        kbMainButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                kb_frame.visibility = View.VISIBLE
                sh_frame.visibility = View.INVISIBLE
            }
        })

        shMainButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                sh_frame.visibility = View.VISIBLE
                kb_frame.visibility = View.INVISIBLE
            }
        })
    }
}