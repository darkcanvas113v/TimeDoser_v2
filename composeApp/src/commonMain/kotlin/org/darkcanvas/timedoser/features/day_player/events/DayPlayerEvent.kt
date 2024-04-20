package org.darkcanvas.timedoser.features.day_player.events

import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Task

sealed interface DayPlayerEvent {
  data class TaskEnded(val task: Task, val nextTask: Task?): DayPlayerEvent
}