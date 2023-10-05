package kr.ac.duksung.minifrontapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestoreSettings

class FriendsAddList : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private lateinit var textView: TextView // 수정: textView를 클래스 레벨에서 선언

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends_add_list)

        // TextView를 레이아웃에서 찾음
        textView = findViewById(R.id.friends_search_name1)

        // Firestore에서 데이터를 가져오는 함수 호출
        fetchDocumentNamesFromFirestore()
    }

    private fun fetchDocumentNamesFromFirestore() {
        db.collection("users")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val documentNames = ArrayList<String>()

                for (document in querySnapshot) {
                    val documentName = document.id
                    documentNames.add(documentName)
                }

                // 문서 이름 목록을 하나의 문자열로 변환
                val firstDocumentName = if (documentNames.isNotEmpty()) documentNames[5] else "No documents"

                // UI 스레드에서 TextView에 설정
                runOnUiThread {
                    val textView = findViewById<TextView>(R.id.friends_search_name1)
                    textView.text = firstDocumentName
                }
            }
            .addOnFailureListener { e ->
                // 오류 처리
                e.printStackTrace()
            }
    }
}
