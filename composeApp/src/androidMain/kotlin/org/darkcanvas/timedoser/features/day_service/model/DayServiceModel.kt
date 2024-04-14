package org.darkcanvas.timedoser.features.day_service.model

import org.darkcanvas.timedoser.core.util.convertMillisToStringFormatWithSeconds
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Day

data class DayServiceModel(
  val currentTaskName: String,
  val timeRemained: String,
  val state: Day.State
)

fun Day.toDayServiceModel(): DayServiceModel {
  val currentTask = items[currentTaskPos]
  return DayServiceModel(
    currentTaskName = currentTask.name,
    timeRemained = convertMillisToStringFormatWithSeconds(currentTask.duration - currentTask.progress),
    state = state
  )
}
