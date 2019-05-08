package put.cardy

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build


class NotificationService : IntentService("NotificationService") {
    private lateinit var mNotification: Notification
    private val mNotificationId: Int = 1000

    @SuppressLint("NewApi")
    private fun createChannel() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val context = this.applicationContext
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
            notificationChannel.enableVibration(true)
            notificationChannel.setShowBadge(true)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.parseColor("#e8334a")
            notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            notificationManager.createNotificationChannel(notificationChannel)
        }

    }

    companion object {

        const val CHANNEL_ID = "CHANNEL_ID"
        const val CHANNEL_NAME = "Sample Notification"
    }


    override fun onHandleIntent(intent: Intent?) {

        createChannel()
        var cardNumber = ""
        if (intent != null && intent.extras != null) {
            cardNumber = intent.extras!!.getString("cardNumber")
        }


        val context = this.applicationContext
        val notifyIntent = Intent(this, MainActivity::class.java)

        val title = "Rembember about your card goal!"
        val message =
            "Card number: $cardNumber"

        notifyIntent.putExtra("title", title)
        notifyIntent.putExtra("message", message)
        notifyIntent.putExtra("notification", true)

        notifyIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        val pendingIntent = PendingIntent.getActivity(context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val res = this.resources
        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


            mNotification = Notification.Builder(this, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.card_black)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.card_black))
                .setAutoCancel(true)
                .setContentTitle(title)
                .setStyle(
                    Notification.BigTextStyle()
                        .bigText(message)
                )
                .setContentText(message).build()
        } else {

            mNotification = Notification.Builder(this)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.card_black)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.card_black))
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_MAX)
                .setContentTitle(title)
                .setStyle(
                    Notification.BigTextStyle()
                        .bigText(message)
                )
                .setSound(uri)
                .setContentText(message).build()

        }


        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(mNotificationId, mNotification)


    }
}