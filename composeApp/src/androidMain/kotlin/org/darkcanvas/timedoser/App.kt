package org.darkcanvas.timedoser

import android.app.Application
import android.content.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.darkcanvas.timedoser.app.AppDIContainer
import org.darkcanvas.timedoser.features.alarm.AlarmActivity
import org.darkcanvas.timedoser.features.alarm.AlarmService
import org.darkcanvas.timedoser.features.alarm.model.AlarmModel
import org.darkcanvas.timedoser.features.alarm.model.AlarmUIModel
import org.darkcanvas.timedoser.features.alarm.model.toAlarmModel
import org.darkcanvas.timedoser.features.alarm.notifications.AlarmNotificationChannel
import org.darkcanvas.timedoser.features.day_player.DayPlayer
import org.darkcanvas.timedoser.features.day_player.events.DayPlayerEvent
import org.darkcanvas.timedoser.features.day_service.DayService
import org.darkcanvas.timedoser.features.day_service.notifications.DayServiceNotificationChannel
import org.kodein.di.instance

class App : Application() {

  val container = AppDIContainer()

  private val scope = CoroutineScope(Dispatchers.IO)

  override fun onCreate() {
    super.onCreate()

    setNotificationChannels()

    startService(Intent(applicationContext, DayService::class.java))

    observePlayerEvents()
  }

  private fun setNotificationChannels() {
    DayServiceNotificationChannel.set(this)
    AlarmNotificationChannel.set(this)
  }

  private fun observePlayerEvents() {
    val player: DayPlayer = container.dayPlayerDI.instance()
    scope.launch {
      player.observeEvents().collect() {
        when (it) {
          is DayPlayerEvent.TaskEnded -> {
            val intent = Intent(applicationContext, AlarmService::class.java)
            intent.putExtra(AlarmModel.EXTRA_ID, it.toAlarmModel().toJson())
            intent.action = AlarmService.START_ALARM
            startService(intent)
          }
        }
      }
    }
  }

}