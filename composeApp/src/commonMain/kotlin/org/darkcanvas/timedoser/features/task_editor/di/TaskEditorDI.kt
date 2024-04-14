package org.darkcanvas.timedoser.features.task_editor.di

import com.arkivanov.decompose.ComponentContext
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Task
import org.darkcanvas.timedoser.features.notification_channel.model.NotificationModel
import org.darkcanvas.timedoser.features.task_editor.DefaultTaskEditorComponent
import org.darkcanvas.timedoser.features.task_editor.TaskEditorComponent
import org.kodein.di.DI
import org.kodein.di.bindFactory

fun createTaskEditorDI(
  onDismiss: () -> Unit,
  onSuccess: (Task, Int) -> Unit
) = DI.direct {
  bindFactory<TaskEditorFactoryData, TaskEditorComponent> {
    it.run {
      DefaultTaskEditorComponent(
        componentContext = componentContext,
        task = task,
        pos = pos,
        onDismiss = onDismiss,
        onSuccess = onSuccess
      )
    }
  }
}

data class TaskEditorFactoryData(
  val componentContext: ComponentContext,
  val task: Task,
  val pos: Int
)