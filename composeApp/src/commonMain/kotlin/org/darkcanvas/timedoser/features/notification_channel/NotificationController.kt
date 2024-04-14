package org.darkcanvas.timedoser.features.notification_channel

import kotlinx.coroutines.flow.Flow
import org.darkcanvas.timedoser.features.notification_channel.model.NotificationModel

interface NotificationController {
  fun showNotification(notification: NotificationModel)
}