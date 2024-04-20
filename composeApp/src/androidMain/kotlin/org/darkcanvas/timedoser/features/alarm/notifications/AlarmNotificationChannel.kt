package org.darkcanvas.timedoser.features.alarm.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

object AlarmNotificationChannel {
  const val ID = "TIMEDOSER_ALARM_NOTIFICATION_CHANNEL"

  fun set(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val name = "AlarmService"
      val descriptionText = "Notifies the user that task time is out"
      val importance = NotificationManager.IMPORTANCE_HIGH
      val mChannel = NotificationChannel(ID, name, importance)
      mChannel.description = descriptionText

      val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
      notificationManager.createNotificationChannel(mChannel)
    }
  }
}
