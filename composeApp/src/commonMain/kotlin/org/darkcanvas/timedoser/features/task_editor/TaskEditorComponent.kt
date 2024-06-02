package org.darkcanvas.timedoser.features.task_editor

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.Flow
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Task
import org.darkcanvas.timedoser.features.task_editor.ui.comfirmation_dialog.ConfirmationComponent

interface TaskEditorComponent {
  val task: Flow<Task>
  val isInEditMode: Boolean

  val confirmationDialog: Flow<ConfirmationComponent?>

  fun setName(name: String)
  fun setDuration(duration: Long)
  fun verifyData(): Result
  fun save()
  fun close()
  fun delete()

  enum class Result {
    SUCCESS, ERROR_EMPTY_NAME, ERROR_DURATION_IS_ZERO
  }
}