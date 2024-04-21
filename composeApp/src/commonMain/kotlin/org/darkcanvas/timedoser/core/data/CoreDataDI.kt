package org.darkcanvas.timedoser.core.data

import com.russhwolf.settings.Settings
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

fun createCoreDataDI(
  settings: Settings
) = DI.direct {
  bind<Settings> { singleton { settings } }
}