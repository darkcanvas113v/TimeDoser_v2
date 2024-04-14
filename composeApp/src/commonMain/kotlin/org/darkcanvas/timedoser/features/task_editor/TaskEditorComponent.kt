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

  enum class Result {
    SUCCESS, ERROR_EMPTY_NAME, ERROR_DURATION_IS_ZERO
  }
}