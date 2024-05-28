package org.darkcanvas.timedoser.features.day_service.notifications

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import org.darkcanvas.timedoser.MainActivity
import org.darkcanvas.timedoser.R
import org.darkcanvas.timedoser.core.getImmutablePendingIntentFlags
import org.darkcanvas.timedoser.features.day_service.DayService

fun getDisabledNotificationBuilder(context: Context) = getDefaultNotificationBuilder(context)
  .addAction(
    R.drawable.ic_baseline_play_arrow_24,
    "Start",
    getPendingIntent(context, DayService.ACTION_START)
  )
  .addAction(
    R.drawable.baseline_remove_24,
    "Remove",
    getPendingIntent(context, DayService.ACTION_REMOVE)
  )

fun getActiveNotificationBuilder(context: Context) = getDefaultNotificationBuilder(context)
  .addAction(
    R.drawable.ic_baseline_pause_24,
    "Pause",
    getPendingIntent(context, DayService.ACTION_PAUSE)
  )
  .addAction(
    R.drawable.ic_baseline_stop_24,
    "Stop",
    getPendingIntent(context, DayService.ACTION_STOP)
  )


private fun getPendingIntent(context: Context, action: String): PendingIntent {
  val intent = Intent(context, DayService::class.java)
  intent.action = action
  return PendingIntent.getService(context, 0, intent, getImmutablePendingIntentFlags())
}

private fun getDefaultNotificationBuilder(context: Context) = NotificationCompat.Builder(context, DayServiceNotificationChannel.ID)
  .setPriority(NotificationCompat.PRIORITY_DEFAULT)
  .setSmallIcon(R.drawable.ic_launcher_foreground)
  .setContentIntent(
    PendingIntent.getActivity(context, 0, Intent(context, MainActivity::class.java), getImmutablePendingIntentFlags())
  )
  .setAutoCancel(false)
  .setOngoing(true)
  .setOnlyAlertOnce(true)