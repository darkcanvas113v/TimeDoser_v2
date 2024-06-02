package org.darkcanvas.timedoser.previews

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import org.darkcanvas.timedoser.core.theme.DefaultTheme
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Day
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Task
import org.darkcanvas.timedoser.features.main_screen.models.toUIModel
import org.darkcanvas.timedoser.features.main_screen.ui.TaskItem

@Preview
@Composable
private fun TaskItemPreview() {
  DefaultTheme {
    TaskItem(
      task = Task(
        startTime = 0L,
        duration = 6000L,
        name = "Task",
        state = Task.State.COMPLETED,
        progress = 0L,
        intrinsicDuration = 0L
      ).toUIModel(Day.State.WAITING),
      onClick = {}
    )
  }
}