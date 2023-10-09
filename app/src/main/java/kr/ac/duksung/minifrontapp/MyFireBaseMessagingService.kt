package kr.ac.duksung.minifrontapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        // FCM 메시지를 수신했을 때 실행되는 코드
        // remoteMessage로부터 메시지 내용을 가져와서 알림을 생성하거나 작업을 수행할 수 있음

        // 예: 알림 생성
        createNotification(remoteMessage)
    }

    private fun createNotification(remoteMessage: RemoteMessage) {
        // 알림에 사용할 XML 레이아웃을 가져옵니다.
        val contentView = RemoteViews(packageName, R.layout.notification_layout)

        // 알림 내용 설정
        contentView.setTextViewText(R.id.notification_title, "주문 알림")
        contentView.setTextViewText(R.id.notification_message, "주문이 준비되었습니다.")

        // NotificationCompat.Builder를 사용하여 알림 설정
        val channelId = "my_channel_id"
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_baseline_star_24)
            .setCustomContentView(contentView) // 커스텀 레이아웃 설정
            .setAutoCancel(true)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "My Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }

}


