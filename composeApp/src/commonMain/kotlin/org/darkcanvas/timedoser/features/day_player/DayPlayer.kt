package org.darkcanvas.timedoser.features.day_player

import kotlinx.coroutines.flow.Flow
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Task
import org.darkcanvas.timedoser.features.day_player.events.DayPlayerEvent

interface DayPlayer {
  fun play()
  fun stop()
  fun stopCurrentTask()
  fun pause()
  fun addTask(task: Task)
  fun removeTask(taskPos: Int)
  fun modifyTask(task: Task, pos: Int)

  fun observeEvents(): Flow<DayPlayerEvent>
}