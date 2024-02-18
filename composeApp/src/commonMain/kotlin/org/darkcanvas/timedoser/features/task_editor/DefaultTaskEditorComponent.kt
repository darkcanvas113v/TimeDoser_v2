package org.darkcanvas.timedoser.features.task_editor

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Task

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
    _task.value.run {
      if (name.isBlank()) return TaskEditorComponent.Result.Error("Name cannot be blank")
      if (duration == 0L) return TaskEditorComponent.Result.Error("Duration cannot be 0")

      return TaskEditorComponent.Result.Success
    }
  }

  override fun save() {
    onSuccess(_task.value, pos)
  }

  override fun close() = onDismiss()
}