package org.darkcanvas.timedoser.data_domain.day_component

import com.arkivanov.decompose.value.Value
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Day
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Task

interface DayComponent {

  val model: Value<Day>

  fun pause()
  fun stop()
  fun start()
  fun addTask(task: Task)

}