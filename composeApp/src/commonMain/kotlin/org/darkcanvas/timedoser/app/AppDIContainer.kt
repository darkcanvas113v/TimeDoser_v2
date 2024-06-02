package org.darkcanvas.timedoser.app

import com.russhwolf.settings.Settings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.darkcanvas.timedoser.core.data.createCoreDataDI
import org.darkcanvas.timedoser.data_domain.day_component.di.createDayDI
import org.darkcanvas.timedoser.data_domain.day_component.domain.DayRepository
import org.darkcanvas.timedoser.features.day_player.di.createDayPlayerDI
import org.kodein.di.instance

class AppDIContainer(
  settings: Settings
) {
  private val scope = CoroutineScope(Dispatchers.IO)

  private val coreDataDi = createCoreDataDI(settings)
  val dayDI = createDayDI(coreDataDi)
  val dayPlayerDI = createDayPlayerDI(
    dayRepository = dayDI.instance<DayRepository>(),
    ioScope = scope
  )

}