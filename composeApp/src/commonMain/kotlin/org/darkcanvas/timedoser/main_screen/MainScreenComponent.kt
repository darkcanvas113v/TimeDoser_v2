package org.darkcanvas.timedoser.main_screen

import com.arkivanov.decompose.value.Value
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Task

interface MainScreenComponent {
  val model: Value<Model>

  fun start()
  fun pause()
  fun stop()
  fun addTask(task: Task)

  data class Model(
    val items: List<Task> = emptyList(),
    )
}