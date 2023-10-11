package kr.ac.duksung.minifrontapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase

class FriendsAddList : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private lateinit var textView: TextView // 수정: textView를 클래스 레벨에서 선언
    private val realdb = Firebase.database("https://testlogin2-a82d6-default-rtdb.firebaseio.com/")

    // Firebase Realtime Database에서 다른 사용자의 "username"을 검색하고 해당 사용자를 친구로 추가하는 함수
    fun addFriendByUsername(username: String) {
        val currentUser = auth.currentUser

        // 현재 로그인한 사용자의 ID를 가져옵니다.
        val currentUserId = currentUser?.uid

        // Firebase Realtime Database의 "users" 경로에서 "username"을 검색합니다.
        val usersRef = realdb.getReference("users")
        usersRef.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (userSnapshot in dataSnapshot.children) {
                    val friendUserId = userSnapshot.key // 검색된 사용자의 ID

                    if (friendUserId != null && currentUserId != null) {
                        // 현재 로그인한 사용자의 ID를 검색된 사용자의 "friendlist"에 추가합니다.
                        val friendListRef = usersRef.child(friendUserId).child("friendlist")

                        // 현재 로그인한 사용자의 ID를 "friendlist"에 추가
                        friendListRef.child(currentUserId).setValue(true)
                            .addOnSuccessListener {
                                // 친구를 성공적으로 추가한 경우
                                println("친구 추가 완료: $username")
                            }
                            .addOnFailureListener { e ->
                                // 친구 추가에 실패한 경우
                                println("친구 추가 실패: $e")
                            }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Firebase Realtime Database에서의 읽기 실패 또는 취소 처리
                println("Database Error: ${databaseError.message}")
            }
        })
    }

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
