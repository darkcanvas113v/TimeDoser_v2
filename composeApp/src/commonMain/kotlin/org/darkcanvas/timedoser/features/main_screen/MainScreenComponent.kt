package org.darkcanvas.timedoser.features.main_screen

import kotlinx.coroutines.flow.Flow
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Day
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Task

interface MainScreenComponent {

  val dayState: Flow<Day>

  fun play()
  fun pause()
  fun stop()
  fun removeTask(atPos: Int)
  fun addTask(task: Task)
  fun stopCurrentTask()

}