package org.darkcanvas.timedoser.features.alarm

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import org.darkcanvas.timedoser.R
import org.darkcanvas.timedoser.core.getImmutablePendingIntentFlags
import org.darkcanvas.timedoser.features.alarm.model.AlarmModel
import org.darkcanvas.timedoser.features.alarm.notifications.AlarmNotificationChannel

class AlarmService: Service() {

  private val vibrator by lazy { getDefaultVibrator(this) }
  private val ringtone by lazy { getDefaultRingtone(this) }

  private fun start() {
    ringtone.play()
    vibrator.vibrateCompat(duration = 60_000L)
  }

  private fun stop() {
    ringtone.stop()
    vibrator.cancel()
  }

  override fun onBind(intent: Intent?): IBinder? = null

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    if (intent != null) {
      when (intent.action) {
        DISABLE_ALARM -> {
          stopSelf()
        }
        START_ALARM -> {
          val model = intent.getStringExtra(AlarmModel.EXTRA_ID)?.let { AlarmModel.fromJson(it) }

          if (model == null) {
            stopSelf()
          }
          else {
            goForeground(model)

            start()
          }
        }
      }
    }

    return START_NOT_STICKY
  }

  private fun goForeground(alarmModel: AlarmModel) {
    val notification = NotificationCompat.Builder(this, AlarmNotificationChannel.ID)
      .setContentTitle("${alarmModel.currentTaskName}'s time is up")
      .apply { if (alarmModel.nextTaskName != null) setContentTitle("Next task is ${alarmModel.nextTaskName}") }
      .setSmallIcon(R.drawable.ic_launcher_foreground)
      .setPriority(NotificationCompat.PRIORITY_HIGH)
      .setCategory(NotificationCompat.CATEGORY_ALARM)
      .setFullScreenIntent(AlarmActivity.getIntent(this, alarmModel), true)
      .addAction(R.drawable.ic_baseline_stop_24, "Disable", PendingIntent.getService(this, 0, getDisableAlarmIntent(this), getImmutablePendingIntentFlags()))
      .build()

    startForeground(ID, notification)
  }

  override fun onDestroy() {
    super.onDestroy()

    stop()
    stopForeground(STOP_FOREGROUND_REMOVE)
  }

  companion object {
    const val DISABLE_ALARM = "DISABLE_ALARM"
    const val START_ALARM = "START_ALARM"

    const val ID = 2

    fun getDisableAlarmIntent(context: Context) = Intent(context, AlarmService::class.java).apply {
      action = DISABLE_ALARM
    }
  }
}