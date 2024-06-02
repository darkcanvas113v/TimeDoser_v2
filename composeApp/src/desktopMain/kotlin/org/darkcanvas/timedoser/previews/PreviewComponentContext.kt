package org.darkcanvas.timedoser.previews

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry

fun getPreviewComponentContext(): ComponentContext {
  val lifecycle = LifecycleRegistry()
  return DefaultComponentContext(lifecycle)
}