import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.provider.MediaStore.Images.Media.getBitmap
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.getSystemService
import androidx.core.graphics.drawable.toBitmap
import coil.Coil
import coil.request.GetRequest
import com.example.myapplication.DoorActivity
import com.example.myapplication.R
import com.example.myapplication.data.TokenRequest
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MessagingService : FirebaseMessagingService() {

    val notificationManager by lazy { NotificationManagerCompat.from(this) }

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                "notifications",
                "Уведомления",
                NotificationManager.IMPORTANCE_HIGH
            )
            getSystemService<NotificationManager>()
                ?.createNotificationChannel(notificationChannel)
        }
    }

    override fun onMessageReceived(msg: RemoteMessage) {

        val type = msg.data["type"] ?: return
        when (type) {
            "door" -> showDoorNotification(msg)
            "humidifier" -> showHumidifierNotification(msg)
        }

    }
    fun showHumidifierNotification(msg: RemoteMessage) {

    }
    fun showDoorNotification(msg: RemoteMessage) {
        val date = msg.data["date"] ?: return // Параметры обязательные, если не хватает,
        val photo = msg.data["photo"] ?: return // считаем, что уведомление недействительно
        GlobalScope.launch {
            val bitmap = getBitmap(photo) // Скачиваем картинку по URL
            val notification = NotificationCompat.Builder(this@MessagingService, "notifications")
                .setContentTitle("Пришел гость: $date") // Заголовок уведомления
                .setContentText("Открыть дверь?")
                .setContentIntent(getDoorContentIntent(photo))
                .setSmallIcon(R.drawable.ic_baseline_lock_24) // Иконка, она обязательна
                .setStyle(
                    NotificationCompat.BigPictureStyle()
                        .bigPicture(bitmap) // Задаем уведомлению большую картинку
                )
                .build()
            notificationManager.notify(date.hashCode(), notification) // Показываем его
        }
    }
    fun getDoorContentIntent(photo: String): PendingIntent {
        val intent = Intent(this, DoorActivity::class.java)
        intent.putExtra("photo", photo)
        return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    suspend fun getBitmap(url: String): Bitmap? {
        val request = GetRequest.Builder(this)
            .data(url)
            .build()
        val result = Coil.imageLoader(this).execute(request).drawable
        return result?.toBitmap(result.intrinsicWidth, result.intrinsicHeight)
    }


    override fun onNewToken(token: String) {
    GlobalScope.launch { TokenRequest(token) }
    }
}