package org.darkcanvas.timedoser.app

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.darkcanvas.timedoser.data_domain.day_component.di.createDayDI
import org.darkcanvas.timedoser.data_domain.day_component.domain.DayRepository
import org.darkcanvas.timedoser.features.day_player.di.createDayPlayerDI
import org.kodein.di.instance

class AppDIContainer() {
  private val scope = CoroutineScope(Dispatchers.IO)

  val dayDI = createDayDI()
  val dayPlayerDI = createDayPlayerDI(
    dayRepository = dayDI.instance<DayRepository>(),
    ioScope = scope
  )

}