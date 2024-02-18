package org.darkcanvas.timedoser.features.main_screen.models

import org.darkcanvas.timedoser.core.util.convertMillisToStringFormat
import org.darkcanvas.timedoser.core.util.convertMillisToStringFormatWithSeconds
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Task

data class TaskUIModel(
  val name: String,
  val startTime: String,
  val duration: String,
  val state: Task.State,
  val progress: String,
  val timeRemaining: String,
  val relativeProgress: Float
) {
  companion object {
    val INITIAL = Task.INITIAL.toUIModel()
  }
}

fun Task.toUIModel() = TaskUIModel(
  name = name,
  startTime = convertMillisToStringFormat(startTime),
  duration = convertMillisToStringFormatWithSeconds(duration),
  state = state,
  progress = convertMillisToStringFormatWithSeconds(progress),
  timeRemaining = convertMillisToStringFormatWithSeconds(duration - progress),
  relativeProgress = progress.toFloat() /duration
)
