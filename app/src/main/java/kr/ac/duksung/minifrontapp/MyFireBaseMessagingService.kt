package kr.ac.duksung.minifrontapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: ${remoteMessage.from}")

        // 메시지에 데이터 페이로드가 포함되어 있는지 확인
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")

            // 데이터를 처리하는데 10초 이상이 걸리면 WorkManager를 사용
            scheduleJob()
        } else {
            // 10초 이내로 처리 가능한 경우
            handleNow()
        }

        // 메세지에 알림 페이로드가 포함되어 있는지 확인
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
            sendNotification(it.body!!)
        }
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")

        // 토큰 업데이트 시에 서버로 전송할 수 있음
        sendRegistrationToServer(token)
    }

    private fun scheduleJob() {
        // 여기서 장기 실행 작업을 예약하거나 처리할 수 있음
    }

    private fun handleNow() {
        // 10초 이내로 처리 가능한 경우 처리할 작업을 추가
    }

    private fun sendRegistrationToServer(token: String?) {
        // 앱 서버로 토큰을 전송할 수 있음
    }

    private fun sendNotification(messageBody: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            //.setSmallIcon(R.drawable.ic_notification) // 알림 아이콘 설정
            .setContentTitle(getString(R.string.app_name)) // 앱 이름으로 설정
            .setContentText(messageBody) // 메시지 내용 설정
            .setAutoCancel(true) // 알림을 사용자가 탭하면 자동으로 닫히도록 설정
            .setSound(defaultSoundUri) // 알림 소리 설정
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Android Oreo 이상에서는 알림 채널 설정이 필요
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Default Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }
}

