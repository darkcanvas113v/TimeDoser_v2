package org.darkcanvas.timedoser

import android.app.Application
import android.content.Intent
import org.darkcanvas.timedoser.app.AppDIContainer
import org.darkcanvas.timedoser.features.day_service.DayService
import org.darkcanvas.timedoser.features.day_service.notifications.DayServiceNotificationChannel

class App : Application() {

  val container = AppDIContainer()

  override fun onCreate() {
    super.onCreate()

    DayServiceNotificationChannel.set(this)

    startService(Intent(applicationContext, DayService::class.java))
  }

}