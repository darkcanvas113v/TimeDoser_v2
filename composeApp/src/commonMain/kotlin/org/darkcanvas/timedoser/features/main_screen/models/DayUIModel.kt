package org.darkcanvas.timedoser.features.main_screen.models

import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Day

data class DayUIModel(
  val state: Day.State,
  val currentTaskPos: Int,
  val items: List<TaskUIModel>
) {
  companion object {
    val INITIAL = Day.DEFAULT.toUIModel()
  }
}

fun Day.toUIModel() = DayUIModel(
  state = state,
  currentTaskPos = currentTaskPos,
  items = items.map { it.toUIModel() }
)