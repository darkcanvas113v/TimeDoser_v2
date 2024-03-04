package org.darkcanvas.timedoser.features.task_editor

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Task
import org.darkcanvas.timedoser.features.notification_channel.model.NotificationModel

class DefaultTaskEditorComponent(
  componentContext: ComponentContext,
  task: Task,
  private val pos: Int,
  private val onDismiss: () -> Unit,
  private val onSuccess: (Task, Int) -> Unit
): TaskEditorComponent, ComponentContext by componentContext {

  private val _task = MutableStateFlow(task)
  override val task: Flow<Task> = _task
  override fun setName(name: String) {
    _task.update { it.copy(name = name) }
  }

  override fun setDuration(duration: Long) {
    _task.update { it.copy(duration = duration) }
  }

  override fun verifyData(): TaskEditorComponent.Result {
    return _task.value.run {
      if (name.isBlank()) TaskEditorComponent.Result.ERROR_EMPTY_NAME
      else if (duration == 0L) TaskEditorComponent.Result.ERROR_DURATION_IS_ZERO
      else TaskEditorComponent.Result.SUCCESS
    }
  }

  override fun save() {
    onSuccess(_task.value, pos)
  }

  override fun close() = onDismiss()
}