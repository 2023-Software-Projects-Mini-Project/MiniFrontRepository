package kr.ac.duksung.minifrontapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class FriendsAddList : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val currentUserId = auth.currentUser?.uid // 현재 사용자의 UID를 가져옵니다.
    private lateinit var addFriendButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends_add_list)

        addFriendButton = findViewById(R.id.add_friends_btn)

        // 사용자를 검색할 특정 조건을 설정합니다. 예를 들어, 사용자 이름으로 검색하는 경우:
        val query = db.collection("users")
            .whereEqualTo("username", "검색할 사용자 이름")

        query.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val userData = document.data
                    val userId = document.id // 사용자의 UID 가져오기
                    val userName = userData["이름"] as String
                   // val userEmail = userData["이메일"] as String

                    // 사용자 정보를 활용하여 UI 업데이트 또는 추가 작업을 수행하세요.

                    // 사용자를 친구로 추가하는 버튼 클릭 이벤트
                    // 사용자를 친구로 추가하는 버튼 클릭 이벤트
                    addFriendButton.setOnClickListener {
                        // Firestore에 현재 사용자의 친구 목록을 업데이트
                        val userRef = db.collection("users").document(currentUserId!!)
                        userRef.update("friends", FieldValue.arrayUnion(userId))
                            .addOnSuccessListener {
                                Toast.makeText(this, "친구를 추가했습니다.", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "친구 추가에 실패했습니다.", Toast.LENGTH_SHORT).show()
                            }
                    }

                }
            }
    }
/*
    private fun sendFriendRequest(senderId: String?, receiverId: String?) {
        if (senderId != null && receiverId != null) {
            // senderId와 receiverId를 사용하여 친구 요청을 보내는 로직을 구현하세요.
            // 예를 들어, Firestore에 친구 요청을 저장하고 상대방에게 알림을 보내는 방식으로 구현할 수 있습니다.

            // 예시: Firestore에 친구 요청 정보 저장
            val friendRequestData = hashMapOf(
                "senderId" to senderId,
                "receiverId" to receiverId,
                "status" to "pending" // 친구 요청 상태 (대기 중, 수락, 거절 등)
            )

            db.collection("friend_requests")
                .add(friendRequestData)
                .addOnSuccessListener {
                    Toast.makeText(this, "친구 요청을 보냈습니다.", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "친구 요청을 보내는데 실패했습니다.", Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "친구 요청 실패: $e")
                }
        }
    }
 */
}
