package org.darkcanvas.timedoser.data_domain.day_component.domain

import kotlinx.coroutines.flow.Flow
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Day

interface DayRepository {
  fun observe(): Flow<Day>

  fun peek(): Day

  fun update(block: (Day) -> Day)

  fun reset()

}
