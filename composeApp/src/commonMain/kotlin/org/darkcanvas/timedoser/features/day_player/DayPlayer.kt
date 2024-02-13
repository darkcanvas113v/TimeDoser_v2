package org.darkcanvas.timedoser.features.day_player

import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Task

interface DayPlayer {
  fun play()
  fun stop()
  fun stopCurrentTask()
  fun pause()
  fun addTask(task: Task)
  fun removeTask(taskPos: Int)
}