package org.darkcanvas.timedoser.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import org.darkcanvas.timedoser.main_screen.MainScreenComponent

interface RootComponent {

  val stack: Value<ChildStack<*, Child>>

  sealed class Child {
    data class MainScreenChild(val component: MainScreenComponent): Child()
  }

}