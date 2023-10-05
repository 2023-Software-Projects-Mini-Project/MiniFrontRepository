package kr.ac.duksung.minifrontapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class writeMenu : AppCompatActivity() {

    private val db = Firebase.database("https://testlogin2-a82d6-default-rtdb.firebaseio.com/")
    private val categoriesRef = db.getReference("MenuName")
   // private val categoriesRef = db.getReference("M")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        data class review(
            val reviewText: String
        )

        data class Menu(
            val price: String,
            val reviews: MutableList<review>
        )

        data class TodayMenu(
            val TodayName: String,
            val price: String
        )

        data class TodayDate(
            val menuA: TodayMenu,
            val menuB: TodayMenu
        )

        val kfood1 = Menu("5500", mutableListOf())
        val kfood2 = Menu("5500", mutableListOf())
        val kfood3 = Menu("5500", mutableListOf())
            // 다른 메뉴 추가
        val cfood1 = Menu("6000", mutableListOf())
        val cfood2 = Menu("6000", mutableListOf())
        val cfood3 = Menu("4000", mutableListOf())

        val boonsik1 = Menu("3000", mutableListOf())
        val boonsik2 = Menu("4000", mutableListOf())
        val boonsik3 = Menu("5000", mutableListOf())


        val menu1A = TodayMenu("생야채비빔밥\n" +
                "연두부*양념장\n" +
                "서문시장st매콤어묵\n" +
                "배추김치\n" +
                "유부장국", "6000")
        val menu1B = TodayMenu("돼지고기감자짜글이\n" +
                "탕평채\n" +
                "깻잎무침\n" +
                "배추김치\n" +
                "흑미밥", "6000")
        val TodayDate1 = TodayDate(menu1A, menu1B)

        val menu2A = TodayMenu("쭈삼불고기덮밥\n" +
                "어묵잡채\n" +
                "파인애플샐러드\n" +
                "배추김치\n" +
                "파송송계란국", "6000")
        val menu2B = TodayMenu("연남동st라구소바\n" +
                "모듬감자튀김\n" +
                "상큼두부카프레제\n" +
                "배추김치\n" +
                "추가밥", "6000")
        val TodayDate2 = TodayDate(menu2A, menu2B)

        val menu3A = TodayMenu("눈꽃함박스테이크\n" +
                "계란볶음밥\n" +
                "단호박*감자샐러드\n" +
                "배추김치\n" +
                "우동국", "6000")
        val menu3B = TodayMenu("안동찜닭\n" +
                "콩나물비빔라면\n" +
                "연근조림\n" +
                "배추김치\n" +
                "흑미밥", "6000")
        val TodayDate3 = TodayDate(menu3A, menu3B)

        val menu4A = TodayMenu("해물짜장면\n" +
                "김치피자탕수육\n" +
                "단무지\n" +
                "배추김치\n" +
                "짬뽕국", "6000")
        val menu4B = TodayMenu("돈까스김치나베\n" +
                "해물동그랑땡\n" +
                "아이스망고샐러드\n" +
                "깍두기\n" +
                "흑미밥", "6000")
        val TodayDate4 = TodayDate(menu4A, menu4B)

        val menu5A = TodayMenu("참치마요밥버거\n" +
                "로제마라떡볶이\n" +
                "츄러스\n" +
                "단무지\n" +
                "어묵국", "6000")
        val menu5B = TodayMenu("소고기장터국밥\n" +
                "사각어묵볶음\n" +
                "숙주부추무침\n" +
                "석박지\n" +
                "흑미밥", "6000")
        val TodayDate5 = TodayDate(menu5A, menu5B)

        categoriesRef.child("김치찌개").setValue(kfood1)
        categoriesRef.child("된장찌개").setValue(kfood2)
        categoriesRef.child("부대찌개").setValue(kfood3)

        categoriesRef.child("짜장면").setValue(cfood1)
        categoriesRef.child("짬뽕").setValue(cfood2)
        categoriesRef.child("탕수육").setValue(cfood3)

        categoriesRef.child("김밥").setValue(boonsik1)
        categoriesRef.child("라면").setValue(boonsik2)
        categoriesRef.child("떡볶이").setValue(boonsik3)

        categoriesRef.child("오늘의 메뉴").child("2023-10-02").setValue(TodayDate1)
        categoriesRef.child("오늘의 메뉴").child("2023-10-03").setValue(TodayDate2)
        categoriesRef.child("오늘의 메뉴").child("2023-10-04").setValue(TodayDate3)
        categoriesRef.child("오늘의 메뉴").child("2023-10-05").setValue(TodayDate4)
        categoriesRef.child("오늘의 메뉴").child("2023-10-06").setValue(TodayDate5)


    }
}

