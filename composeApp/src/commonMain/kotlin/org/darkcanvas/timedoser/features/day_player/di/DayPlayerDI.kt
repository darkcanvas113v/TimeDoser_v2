package org.darkcanvas.timedoser.features.day_player.di

import org.darkcanvas.timedoser.core.util.ticker.CoroutineTicker
import org.darkcanvas.timedoser.core.util.ticker.Ticker
import org.darkcanvas.timedoser.data_domain.day_component.domain.DayRepository
import org.darkcanvas.timedoser.features.day_player.DayPlayer
import org.darkcanvas.timedoser.features.day_player.DayPlayerImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.provider
import org.kodein.di.singleton

fun createDayPlayerDI(
  dayRepository: DayRepository
): DI = DI {
  bind<DayPlayer>() { singleton { DayPlayerImpl(dayRepository) } }
  bind<Ticker> { provider { CoroutineTicker() } }
}