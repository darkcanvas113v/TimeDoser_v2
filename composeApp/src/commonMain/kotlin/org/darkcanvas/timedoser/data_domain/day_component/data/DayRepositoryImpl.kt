package org.darkcanvas.timedoser.data_domain.day_component.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.darkcanvas.timedoser.data_domain.day_component.domain.DayRepository
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Day

class DayRepositoryImpl(): DayRepository {

  private val model = MutableStateFlow(Day.DEFAULT)

  override fun observe(): Flow<Day> = model
  override fun peek(): Day = model.value

  override fun update(block: (Day) -> Day) {
    model.value = block(model.value)
  }

  override fun reset() {
    model.value = Day.DEFAULT
  }
}