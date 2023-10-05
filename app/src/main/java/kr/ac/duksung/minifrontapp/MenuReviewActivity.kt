package kr.ac.duksung.minifrontapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView

class MenuReviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_review)

        val backIcon = findViewById<ImageView>(R.id.back_icon)

        backIcon.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish() // 현재 액티비티 종료
            }
        })
/*
        public void onGoKimchiClick(View viewpublic void onGoKimchiClick(View view) {
            // 리뷰 화면으로 이동하는 인텐트 생성
            Intent intent = new Intent(this, ReviewActivity.class);

            // 인텐트에 음식 이름 또는 기타 데이터를 추가할 수 있음
            intent.putExtra("foodName", "김치찌개");

            // 리뷰 화면으로 이동
            startActivity(intent);
        }
        ) {
            // 리뷰 화면으로 이동하는 인텐트 생성
            Intent intent = new Intent(this, ReviewActivity.class);

            // 인텐트에 음식 이름 또는 기타 데이터를 추가할 수 있음
            intent.putExtra("foodName", "김치찌개");

            // 리뷰 화면으로 이동
            startActivity(intent);
        }
*/
















    }
}
