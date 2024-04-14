package org.darkcanvas.timedoser.features.day_service

import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.darkcanvas.timedoser.App
import org.darkcanvas.timedoser.data_domain.day_component.domain.DayRepository
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Day
import org.darkcanvas.timedoser.features.day_player.DayPlayer
import org.darkcanvas.timedoser.features.day_service.model.DayServiceModel
import org.darkcanvas.timedoser.features.day_service.model.toDayServiceModel
import org.darkcanvas.timedoser.features.day_service.notifications.getActiveNotificationBuilder
import org.darkcanvas.timedoser.features.day_service.notifications.getDisabledNotificationBuilder
import org.kodein.di.instance

class DayService : Service() {

  private val serviceJob = Job()
  private val scope = CoroutineScope(Dispatchers.Main + serviceJob)

  private val notificationManager by lazy {
    getSystemService(NOTIFICATION_SERVICE) as NotificationManager
  }

  private val activeNotificationBuilder by lazy { getActiveNotificationBuilder(this) }
  private val disabledNotificationBuilder by lazy { getDisabledNotificationBuilder(this) }

  private lateinit var dayPlayer: DayPlayer
  private lateinit var dayRepository: DayRepository

  override fun onBind(p0: Intent?): IBinder? = null

  override fun onCreate() {
    super.onCreate()

    val container = (application as App).container

    dayPlayer = container.dayPlayerDI.instance()
    dayRepository = container.dayDI.instance()

    observeDay()
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    if (intent == null) return START_NOT_STICKY

    when (intent.action) {
      ACTION_STOP -> { dayPlayer.stopCurrentTask() }
      ACTION_START -> { dayPlayer.play() }
      ACTION_PAUSE -> { dayPlayer.pause() }
    }
    return START_NOT_STICKY
  }

  private fun NotificationCompat.Builder.updateNotification(model: DayServiceModel) {
    setContentTitle(model.currentTaskName).setContentText(model.timeRemained)
    val notification = build()
    notificationManager.notify(ID, notification)
    startForeground(ID, notification)
  }

  private fun observeDay() {
    scope.launch {
      dayRepository.observe().map { it.toDayServiceModel() }.collect() {
        when (it.state) {
          Day.State.ACTIVE -> {
            activeNotificationBuilder.updateNotification(it)
          }

          Day.State.DISABLED -> {
            disabledNotificationBuilder.updateNotification(it)
          }

          Day.State.WAITING, Day.State.COMPLETED -> {
            stopForeground(STOP_FOREGROUND_REMOVE)
          }
        }
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    serviceJob.cancel()
  }

  companion object {
    const val ACTION_START = "START"
    const val ACTION_PAUSE = "PAUSE"
    const val ACTION_STOP = "STOP"

    const val ID = 1
  }
}