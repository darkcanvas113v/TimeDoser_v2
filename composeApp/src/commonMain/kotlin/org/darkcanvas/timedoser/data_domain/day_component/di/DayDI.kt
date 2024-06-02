package org.darkcanvas.timedoser.data_domain.day_component.di

import com.russhwolf.settings.Settings
import org.darkcanvas.timedoser.data_domain.day_component.data.DayRepositoryImpl
import org.darkcanvas.timedoser.data_domain.day_component.domain.DayRepository
import org.kodein.di.DI
import org.kodein.di.DirectDI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

fun createDayDI(
  coreDataDI: DirectDI
) = DI.direct {
  bind<DayRepository> { singleton { DayRepositoryImpl(
    settings = coreDataDI.instance()
  ) } }
}