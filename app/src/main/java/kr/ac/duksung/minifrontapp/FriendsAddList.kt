package kr.ac.duksung.minifrontapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestoreSettings

class FriendsAddList : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private lateinit var textView: TextView // 수정: textView를 클래스 레벨에서 선언
    private lateinit var firstDocumentName: String // 변수를 클래스 필드로 선언

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends_add_list)

        val addFriendButton = findViewById<Button>(R.id.add_friends_btn)

        // TextView를 레이아웃에서 찾음
        textView = findViewById(R.id.friends_search_name)

        // Firestore에서 데이터를 가져오는 함수 호출
        fetchDocumentNamesFromFirestore()

        // 친구 추가 버튼 클릭 이벤트 처리
        addFriendButton.setOnClickListener {
            // FriendsList 액티비티로 이동하는 Intent 생성
            val intent = Intent(this, FriendsList::class.java)


            // Intent를 사용하여 FriendsList로 이동
            startActivity(intent)
        }
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
                val firstDocumentName = if (documentNames.isNotEmpty()) documentNames[5]
                                        else "No documents"


                // UI 스레드에서 TextView에 설정
                runOnUiThread {
                    val textView = findViewById<TextView>(R.id.friends_search_name)
                    textView.text = firstDocumentName
                }
            }
            .addOnFailureListener { e ->
                // 오류 처리
                e.printStackTrace()
            }
    }
    private fun searchFirestoreByName(name: String) {
        db.collection("users")
            .whereEqualTo("username", name) // 사용자 이름 필드와 검색 텍스트를 비교하여 검색
            .get()
            .addOnSuccessListener { querySnapshot ->
                val documentNames = ArrayList<String>()

                for (document in querySnapshot) {
                    val documentName = document.id
                    documentNames.add(documentName)
                }

                // 검색 결과를 처리하는 로직을 여기에 추가할 수 있습니다.

                // UI 스레드에서 TextView에 설정
                runOnUiThread {
                    val textView = findViewById<TextView>(R.id.friends_search_name)
                    textView.text = firstDocumentName
                }
            }
            .addOnFailureListener { e ->
                // 오류 처리
                e.printStackTrace()
            }
    }
}