package org.darkcanvas.timedoser.features.task_editor.di

import com.arkivanov.decompose.ComponentContext
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Task
import org.darkcanvas.timedoser.features.task_editor.DefaultTaskEditorComponent
import org.darkcanvas.timedoser.features.task_editor.TaskEditorComponent
import org.kodein.di.DI
import org.kodein.di.bindFactory

fun createTaskEditorDI() = DI.direct {
  bindFactory<TaskEditorFactoryData, TaskEditorComponent> { taskEditorFactoryData ->
    DefaultTaskEditorComponent(
      componentContext = taskEditorFactoryData.componentContext,
      task = taskEditorFactoryData.task,
      pos = taskEditorFactoryData.pos,
      onDismiss = taskEditorFactoryData.onDismiss,
      onSuccess = taskEditorFactoryData.onSuccess
    )
  }
}

data class TaskEditorFactoryData(
  val componentContext: ComponentContext,
  val task: Task,
  val pos: Int,
  val onDismiss: () -> Unit,
  val onSuccess: (Task, Int) -> Unit
)