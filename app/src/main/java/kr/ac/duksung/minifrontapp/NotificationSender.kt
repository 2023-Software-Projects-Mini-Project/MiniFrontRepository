package kr.ac.duksung.minifrontapp
/*
import FcmApi
import FcmNotificationRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
//FCM 알림 요청 보내기 코드
class NotificationSender {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://fcm.googleapis.com/") // FCM 서버 엔드포인트
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val fcmApi: FcmApi = retrofit.create(FcmApi::class.java)

    fun sendFcmNotification(fcmToken: String, title: String, message: String) {
        val notificationRequest = FcmNotificationRequest(
            to = fcmToken, // 대상 디바이스의 FCM 토큰
            data = mapOf(
                "title" to title, // 알림 제목
                "message" to message // 알림 메시지 내용
            )
        )

        val call = fcmApi.sendNotification(notificationRequest)
        call.enqueue(object : Callback<FcmNotificationResponse> {
            override fun onResponse(call: Call<FcmNotificationResponse>, response: Response<FcmNotificationResponse>) {
                if (response.isSuccessful) {
                    // 알림 전송 성공
                } else {
                    // 알림 전송 실패
                }
            }

            override fun onFailure(call: Call<FcmNotificationResponse>, t: Throwable) {
                // 네트워크 오류 또는 예외 처리
            }
        })
    }
}
*/