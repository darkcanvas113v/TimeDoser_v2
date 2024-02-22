package org.darkcanvas.timedoser.data_domain.day_component.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.darkcanvas.timedoser.data_domain.day_component.domain.DayRepository
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Day
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Task

class DayRepositoryImpl(): DayRepository {

  private val default = Day(
    state = Day.State.WAITING,
    currentTaskPos = 0,
    items = listOf(
      Task(
        name = "Asobe",
        startTime = 0L,
        duration = 5000L,
        state = Task.State.WAITING,
        progress = 0L
      ),
      Task(
        name = "Asobe",
        startTime = 0L,
        duration = 5000L,
        state = Task.State.WAITING,
        progress = 0L
      )
    )
  )
  private val model = MutableStateFlow(default)

  override fun observe(): Flow<Day> = model
  override fun peek(): Day = model.value

  override fun update(block: (Day) -> Day) {
    model.value = block(model.value)
  }

  override fun reset() {
    model.value = Day.DEFAULT
  }
}