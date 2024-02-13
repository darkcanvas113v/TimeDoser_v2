package org.darkcanvas.timedoser.app

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.Serializable
import org.darkcanvas.timedoser.data_domain.day_component.di.createDayDI
import org.darkcanvas.timedoser.data_domain.day_component.domain.DayRepository
import org.darkcanvas.timedoser.features.day_player.di.createDayPlayerDI
import org.darkcanvas.timedoser.features.main_screen.MainScreenComponent
import org.darkcanvas.timedoser.features.main_screen.di.createMainScreenDI
import org.kodein.di.factory
import org.kodein.di.instance
import org.kodein.di.newInstance

class DefaultRootComponent(
  componentContext: ComponentContext
): RootComponent, ComponentContext by componentContext {
  private val navigation = StackNavigation<Config>()

  private val scope = CoroutineScope(Dispatchers.IO)

  private val dayDI = createDayDI()
  private val dayPlayerDI = createDayPlayerDI(
    dayRepository = dayDI.instance<DayRepository>(),
    ioScope = scope
  )
  private val mainScreenDI = createMainScreenDI(
    dayDI = dayDI,
    dayPlayerDI = dayPlayerDI
  )

  override val stack: Value<ChildStack<*, RootComponent.Child>> =
    childStack(
      source = navigation,
      serializer = Config.serializer(),
      initialConfiguration = Config.MainScreen,
      handleBackButton = true,
      childFactory = ::child
    )

  override fun onBackClicked(toIndex: Int) {

  }

  private fun child(config: Config, componentContext: ComponentContext): RootComponent.Child =
    when (config) {
      Config.MainScreen -> RootComponent.Child.MainScreenChild(mainScreenDI.instance(arg = componentContext))
    }

  @Serializable
  private sealed interface Config {
    @Serializable
    data object MainScreen: Config
  }
}