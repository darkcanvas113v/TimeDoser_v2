package org.darkcanvas.timedoser.app

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.CoroutineScope
import org.darkcanvas.timedoser.features.main_screen.MainScreenComponent
import org.darkcanvas.timedoser.features.notification_channel.NotificationController

interface RootComponent {
  val stack: Value<ChildStack<*, Child>>
  val scope: CoroutineScope

  fun onBackClicked(toIndex: Int)

  sealed class Child {
    class MainScreenChild(val component: MainScreenComponent): Child()
  }
}