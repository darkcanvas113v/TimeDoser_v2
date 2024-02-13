package org.darkcanvas.timedoser.app

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import org.darkcanvas.timedoser.features.main_screen.MainScreenComponent

interface RootComponent {
  val stack: Value<ChildStack<*, Child>>

  fun onBackClicked(toIndex: Int)

  sealed class Child {
    class MainScreenChild(val component: MainScreenComponent): Child()
  }
}