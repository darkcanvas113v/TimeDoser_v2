package org.darkcanvas.timedoser.features.day_player.model_mutators

import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Task

fun Task.start(): Task {
  return copy(
    state = Task.State.ACTIVE,
    startTime = System.currentTimeMillis()
  )
}

fun Task.pause(): Task {
  return copy(
    state = Task.State.WAITING
  )
}

fun Task.stop(): Task {
  return copy(
    state = Task.State.COMPLETED,
    duration = progress
  )
}

fun Task.disable(): Task {
  return copy(
    state = Task.State.DISABLED
  )
}

fun Task.progress(amount: Long): Task {
  if (progress == 0L) return this

  val newProgress = progress + amount

  if (newProgress > duration) {
    return copy(
        state = Task.State.COMPLETED,
        progress = duration
      )
  }

  return copy(
    progress = newProgress
  )

}