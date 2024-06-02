package org.darkcanvas.timedoser.previews

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.darkcanvas.timedoser.core.theme.DefaultTheme
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Task
import org.darkcanvas.timedoser.features.task_editor.DefaultTaskEditorComponent
import org.darkcanvas.timedoser.features.task_editor.TaskEditorComponent
import org.darkcanvas.timedoser.features.task_editor.ui.TaskEditor
import org.darkcanvas.timedoser.features.task_editor.ui.comfirmation_dialog.ConfirmationComponent
import org.darkcanvas.timedoser.features.task_editor.ui.comfirmation_dialog.ConfirmationDialog


@Preview
@Composable
fun TaskEditorPreview() {
  DefaultTheme {
    TaskEditor(component = object : TaskEditorComponent {
      override val task: Flow<Task> = flow {  }
      override val isInEditMode: Boolean = true
      override val confirmationDialog: Value<ChildSlot<*, ConfirmationComponent>> = getPreviewComponentContext().childSlot(
        source = SlotNavigation(),
        serializer = DefaultTaskEditorComponent.ConfirmationDialogConfig.serializer(),
        handleBackButton = true
      ) { config, componentContext ->
        ConfirmationComponent(taskName = "", onDismiss = {}, onConfirm = {})
      }

      override fun setName(name: String) {

      }

      override fun setDuration(duration: Long) {

      }

      override fun verifyData(): TaskEditorComponent.Result {
        return TaskEditorComponent.Result.SUCCESS
      }

      override fun save() {

      }

      override fun close() {

      }

      override fun delete() {

      }

    })
  }
}

@Preview
@Composable
fun ConfirmationDialogPreview() {
  ConfirmationDialog(
    component = ConfirmationComponent(
      taskName = "Task1",
      onConfirm = {},
      onDismiss = {}
    )
  )
}