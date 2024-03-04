package org.darkcanvas.timedoser.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.TextField
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import org.darkcanvas.timedoser.features.main_screen.ui.MainScreen
import org.darkcanvas.timedoser.features.notification_channel.NotificationControllerProvider

@Composable
fun App(
  component: RootComponent
) {
  MaterialTheme {
    Box(modifier = Modifier.systemBarsPadding()) {
      NotificationControllerProvider {
        Children(
          stack = component.stack,
          modifier = Modifier.fillMaxSize().imePadding(),
          animation = stackAnimation(fade())
        ) {
          when (val child = it.instance) {
            is RootComponent.Child.MainScreenChild -> MainScreen(
              component = child.component
            )
          }
        }
      }
    }
  }
}

fun todaysDate(): String {
  return "01.01.01"
}