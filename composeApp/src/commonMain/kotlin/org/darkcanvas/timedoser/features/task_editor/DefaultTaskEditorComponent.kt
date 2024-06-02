package org.darkcanvas.timedoser.features.task_editor

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.Serializable
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Task
import org.darkcanvas.timedoser.features.main_screen.DefaultMainScreenComponent
import org.darkcanvas.timedoser.features.notification_channel.model.NotificationModel
import org.darkcanvas.timedoser.features.task_editor.di.TaskEditorFactoryData
import org.darkcanvas.timedoser.features.task_editor.ui.comfirmation_dialog.ConfirmationComponent
import org.kodein.di.instance

class DefaultTaskEditorComponent(
  componentContext: ComponentContext,
  private val formerTask: Task,
  private val pos: Int,
  private val onDismiss: () -> Unit,
  private val onSuccess: (Task, Int) -> Unit,
  private val onDelete: () -> Unit
) : TaskEditorComponent, ComponentContext by componentContext {

  private val _task = MutableStateFlow(formerTask)
  override val task: Flow<Task> = _task
  override val isInEditMode: Boolean = pos != -1

  override val confirmationDialog = MutableStateFlow(null)

  override fun setName(name: String) {
    _task.update { it.copy(name = name) }
  }

  override fun setDuration(duration: Long) {
    _task.update { it.copy(intrinsicDuration = duration, duration = duration) }
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

  override fun delete() {
    ConfirmationComponent(
      taskName = formerTask.name,
      pos = pos,
      onConfirm = {
        confirmationDialog.value = null
        onDelete()
      },
      onDismiss = {
        confirmationDialog.value = null
      }
    )
  }

  @Serializable
  data class ConfirmationDialogConfig(
    val taskName: String,
    val pos: Int
  )
}