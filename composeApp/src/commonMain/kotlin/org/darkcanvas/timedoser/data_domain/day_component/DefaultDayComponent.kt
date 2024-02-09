package org.darkcanvas.timedoser.data_domain.day_component

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Day
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Task

class DefaultDayComponent(): DayComponent {

  private val _model = MutableValue(
    Day(
    items = emptyList()
  )
  )

  override val model: Value<Day> = _model

  override fun pause() {

  }

  override fun stop() {

  }

  override fun start() {

  }

  override fun addTask(task: Task) {

  }

}