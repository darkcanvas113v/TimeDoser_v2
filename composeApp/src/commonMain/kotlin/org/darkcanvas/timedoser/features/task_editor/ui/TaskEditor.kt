package org.darkcanvas.timedoser.features.task_editor.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import org.darkcanvas.timedoser.core.util.convertMillisToStringFormat
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Task
import org.darkcanvas.timedoser.features.notification_channel.LocalNotificationController
import org.darkcanvas.timedoser.features.notification_channel.NotificationControllerProvider
import org.darkcanvas.timedoser.features.notification_channel.model.NotificationModel
import org.darkcanvas.timedoser.features.task_editor.TaskEditorComponent

@Composable
fun TaskEditor(
  component: TaskEditorComponent,
) {
  val task by remember(component) {
    component.task
  }.collectAsState(initial = Task.INITIAL)

  val (timePickerDialogVisibility, setTimePickerDialogVisibility) = remember {
    mutableStateOf(false)
  }

  val notificationController = LocalNotificationController.current

  Dialog(
    onDismissRequest = component::close,
    properties = DialogProperties(usePlatformDefaultWidth = false),
  ) {
      Surface(
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
          .padding(16.dp)
      ) {
        Column(
          modifier = Modifier
            .padding(vertical = 16.dp)
            .padding(horizontal = 12.dp)
        ) {
          Text(
            text = "EditTask",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 16.dp)
          )

          OutlinedTextField(
            value = task.name,
            onValueChange = {
              component.setName(name = it)
            },
            label = { Text(text = "Name") },
            modifier = Modifier.fillMaxWidth()
          )

          Column(
            modifier = Modifier
              .padding(top = 32.dp)
          ) {
            Text(
              text = "Duration",
              style = MaterialTheme.typography.caption,
              modifier = Modifier.padding(bottom = 4.dp)
            )
            OutlinedButton(
              onClick = {
                setTimePickerDialogVisibility(true)
              },
            ) {
              Text(
                text = convertMillisToStringFormat(task.duration),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(horizontal = 0.dp, vertical = 8.dp)
              )
            }
          }

          Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
              .fillMaxWidth()
              .padding(top = 32.dp)
          ) {
            Button(
              onClick = component::close,
              colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Black.copy(alpha = 0.07f),
                contentColor = Color.Black
              ),
              elevation = null,
              modifier = Modifier.width(90.dp)
            ) {
              Text(text = "Cancel")
            }

            Button(
              onClick = {
                when (val result = component.verifyData()) {
                  is TaskEditorComponent.Result.Error -> {
                    notificationController.showNotification(NotificationModel(result.msg))
                  }

                  TaskEditorComponent.Result.Success -> {
                    component.save()
                  }
                }
              },
              modifier = Modifier.width(90.dp)
            ) {
              Text(text = "Ok")
            }
          }
        }
      }

    TimePickerDialog(
      visible = timePickerDialogVisibility,
      onDismiss = { setTimePickerDialogVisibility(false) },
      time = task.duration,
      onGetResult = {
        component.setDuration(it)
      }
    )
  }
}