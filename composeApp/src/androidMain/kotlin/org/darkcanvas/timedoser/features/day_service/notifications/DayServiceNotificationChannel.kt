package org.darkcanvas.timedoser.features.day_service.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

object DayServiceNotificationChannel {
  const val ID = "TIMEDOSER_DAYSERVICE_NOTIFICATION_CHANNEL"

  fun set(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val name = "Day service"
      val descriptionText = "Helps to keep app alive and gives user some control options"
      val importance = NotificationManager.IMPORTANCE_HIGH
      val mChannel = NotificationChannel(ID, name, importance)
      mChannel.description = descriptionText

      val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
      notificationManager.createNotificationChannel(mChannel)
    }
  }
}