package org.darkcanvas.timedoser.features.main_screen.di

import com.arkivanov.decompose.ComponentContext
import org.darkcanvas.timedoser.features.main_screen.DefaultMainScreenComponent
import org.darkcanvas.timedoser.features.main_screen.MainScreenComponent
import org.kodein.di.DI
import org.kodein.di.DirectDI
import org.kodein.di.bindFactory
import org.kodein.di.instance

fun createMainScreenDI(
  dayPlayerDI: DirectDI,
  dayDI: DirectDI
) = DI.direct {
  bindFactory<ComponentContext, MainScreenComponent> { componentContext ->
    DefaultMainScreenComponent(
      componentContext = componentContext,
      dayPlayer = dayPlayerDI.instance(),
      dayRepository = dayDI.instance()
    )
  }
}