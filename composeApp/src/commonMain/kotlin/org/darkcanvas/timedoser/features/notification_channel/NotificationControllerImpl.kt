package org.darkcanvas.timedoser.features.notification_channel

import androidx.compose.material.SnackbarHostState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import org.darkcanvas.timedoser.features.notification_channel.model.NotificationModel

class NotificationControllerImpl(
  private val scope: CoroutineScope,
  private val snackbarHostState: SnackbarHostState
): NotificationController {
  override fun showNotification(notification: NotificationModel) {
    scope.launch { snackbarHostState.showSnackbar(
      message = notification.message
    ) }
  }
}