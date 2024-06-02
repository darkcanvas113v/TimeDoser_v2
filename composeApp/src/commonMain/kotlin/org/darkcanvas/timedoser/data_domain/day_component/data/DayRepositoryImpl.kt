package org.darkcanvas.timedoser.data_domain.day_component.data

import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.darkcanvas.timedoser.data_domain.day_component.data.model.dayFromJson
import org.darkcanvas.timedoser.data_domain.day_component.data.model.toJson
import org.darkcanvas.timedoser.data_domain.day_component.domain.DayRepository
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Day
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Task

class DayRepositoryImpl(
  private val settings: Settings
): DayRepository {

  private val model = MutableStateFlow(Day.DEFAULT)

  init {
    val json = settings.getString(CURRENT_DAY, Day.DEFAULT.toJson())
    model.value = dayFromJson(json)
  }

  override fun observe(): Flow<Day> = model
  override fun peek(): Day = model.value

  override fun update(block: (Day) -> Day) {
    model.value = block(model.value)

    when (model.value.state) {
      Day.State.ACTIVE -> {}
      Day.State.WAITING, Day.State.COMPLETED, Day.State.DISABLED -> { save() }
    }
  }

  override fun reset() {
    model.value = Day.DEFAULT
  }

  private fun save() {
    settings.putString(CURRENT_DAY, model.value.toJson())
  }

  companion object {
    private const val CURRENT_DAY = "CURRENT_DAY"
  }
}