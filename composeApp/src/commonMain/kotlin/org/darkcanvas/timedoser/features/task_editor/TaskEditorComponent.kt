package org.darkcanvas.timedoser.features.task_editor

import kotlinx.coroutines.flow.Flow
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Task

interface TaskEditorComponent {
  val task: Flow<Task>

  fun setName(name: String)
  fun setDuration(duration: Long)
  fun verifyData(): Result
  fun save()
  fun close()

  sealed interface Result {
    data object Success: Result
    class Error(val msg: String): Result
  }
}