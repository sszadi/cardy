package put.cardy







import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import put.cardy.model.Period
import java.util.*

class NotificationUtils {


    fun setNotification(cardNumber: String, period: Period, activity: Activity) {


        val alarmManager = activity.getSystemService(Activity.ALARM_SERVICE) as AlarmManager
        val alarmIntent =
            Intent(activity.applicationContext, AlarmReceiver::class.java)

        alarmIntent.putExtra("cardNumber", cardNumber)

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        if (Period.MONTH == period) {
            calendar.set(Calendar.DAY_OF_MONTH, 29)
        } else {
            calendar.set(Calendar.DAY_OF_YEAR, 1)
        }

        val pendingIntent = PendingIntent.getBroadcast(activity, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT)
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)


    }
}