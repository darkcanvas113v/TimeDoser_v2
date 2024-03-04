package org.darkcanvas.timedoser.features.notification_channel

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import kotlinx.coroutines.CoroutineScope

@Composable
fun NotificationControllerProvider(
  content: @Composable () -> Unit
) {
  val hostState = remember() { SnackbarHostState() }
  val coroutineScope = rememberCoroutineScope()

  CompositionLocalProvider(
    LocalNotificationController provides NotificationControllerImpl(
      scope = coroutineScope,
      snackbarHostState = hostState
    ),
  ) {
    content()
    Box(modifier = Modifier.fillMaxSize().imePadding()) {
      SnackbarHost(
        hostState = hostState,
        modifier = Modifier.align(Alignment.BottomCenter).zIndex(10f)
      )
    }

  }
}

val LocalNotificationController = staticCompositionLocalOf<NotificationController> {
  error("NotificationController is not provided")
}