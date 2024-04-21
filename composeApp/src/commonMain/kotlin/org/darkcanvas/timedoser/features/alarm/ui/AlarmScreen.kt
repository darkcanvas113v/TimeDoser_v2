package org.darkcanvas.timedoser.features.alarm.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.darkcanvas.timedoser.core.theme.LocalExtendedColors
import org.darkcanvas.timedoser.features.alarm.AlarmComponent
import org.darkcanvas.timedoser.features.alarm.model.AlarmModel
import org.darkcanvas.timedoser.features.alarm.model.toAlarmUIModel

@Composable
fun AlarmScreen(
  component: AlarmComponent,
  disableAlarm: () -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxSize()
  ) {
    Text(
      text = component.alarmUIModel.currentTaskText,
      modifier = Modifier
        .padding(16.dp)
        .align(Alignment.Center)
    )

    Button(
      onClick = disableAlarm,
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .align(Alignment.BottomCenter),
      shape = RoundedCornerShape(4.dp),
      colors = ButtonDefaults.buttonColors(backgroundColor = LocalExtendedColors.current.active, contentColor = MaterialTheme.colors.onPrimary)
    ) {
      Text(
        text = "OK",
        style = MaterialTheme.typography.button
      )
    }
  }
}

//@Preview
//@Composable
//private fun AlarmScreenPreview() {
//  AlarmScreen(/
//    component = object : AlarmComponent {
//      override val alarmUIModel = AlarmModel("Task1", "Task2").toAlarmUIModel()
//
//    },
//    disableAlarm = {}
//  )
//}