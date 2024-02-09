package org.darkcanvas.timedoser.data_domain.day_component.di

import org.darkcanvas.timedoser.data_domain.day_component.data.DayRepositoryImpl
import org.darkcanvas.timedoser.data_domain.day_component.domain.DayRepository
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

fun createDayDI(): DI =  DI {
  bind<DayRepository> { singleton { DayRepositoryImpl() } }
}