package org.darkcanvas.timedoser.features.day_player.mutators

import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Task

fun Task.start(): Task {
  if (state == Task.State.DISABLED) return stop().copy(startTime = System.currentTimeMillis())

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

fun Task.toggle(): Task {
  return copy(
    state = if (state == Task.State.DISABLED) Task.State.WAITING else Task.State.DISABLED
  )
}

fun Task.progress(amount: Long): Task {
  if (amount == 0L) return this

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

fun Task.reset(): Task {
  return copy(
    state = Task.State.WAITING,
    duration = intrinsicDuration,
    progress = 0L
  )
}

fun Task.getDuration(): Long = when {
  state == Task.State.DISABLED -> startTime
  else -> startTime + duration
}