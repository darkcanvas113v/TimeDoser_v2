package org.darkcanvas.timedoser.features.day_player.mutators

import org.darkcanvas.timedoser.core.util.addItem
import org.darkcanvas.timedoser.core.util.modifyAt
import org.darkcanvas.timedoser.core.util.modifyFromTo
import org.darkcanvas.timedoser.core.util.removeItemAt
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Day
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Task

fun Day.start(): Day {
  if (state == Day.State.ACTIVE) return this

  val startedTask = items[currentTaskPos].start()

  return copy(
    state = Day.State.ACTIVE,
    items = items.modifyAt(currentTaskPos, startedTask)
  )
}

fun Day.pause(): Day {
  if (state == Day.State.WAITING) return this

  val pausedTask = items[currentTaskPos].pause()

  return copy(
    state = Day.State.WAITING,
    items = items.modifyAt(currentTaskPos, pausedTask)
  )
}

fun Day.stopTask(): Day {
  val stoppedTask = items[currentTaskPos].stop()

  return copy(items = items.modifyAt(currentTaskPos, stoppedTask)).toNextTask().updateStartTime(currentTaskPos)
}

fun Day.stop(): Day {
  val stoppedTasks = items.modifyFromTo(currentTaskPos) {
    it.stop()
  }

  return copy(
    items = stoppedTasks,
    state = Day.State.COMPLETED,
  )
}

fun Day.progress(amount: Long): Day {
  if (state != Day.State.ACTIVE) return this

  val progressedTask = items[currentTaskPos].progress(amount)

  val result = copy(
    items = items.modifyAt(currentTaskPos, progressedTask)
  )

  if (progressedTask.state != Task.State.ACTIVE) {
    val unprocessedAmount = items[currentTaskPos].run { progress - duration }
    return result.toNextTask(unprocessedAmount)
  }

  return result
}

fun Day.toNextTask(progressAmount: Long = 0L): Day {
  if (items.lastIndex == currentTaskPos) {
    return copy(state = Day.State.COMPLETED)
  }

  val nextTaskPos = currentTaskPos + 1
  val nextTask = items[nextTaskPos].start().progress(progressAmount)

  return copy(
    currentTaskPos = nextTaskPos,
    items = items.modifyAt(nextTaskPos, nextTask)
  )
}

fun Day.addTask(task: Task): Day {
  return copy(
    items = items.addItem(task)
  )
}

fun Day.removeTask(taskPos: Int): Day {
  if (taskPos < currentTaskPos) return this
  if (taskPos == currentTaskPos && state == Day.State.ACTIVE) return this

  return copy(
    items = items.removeItemAt(taskPos)
  ).updateStartTime(taskPos)
}

fun Day.updateStartTime(
  from: Int
): Day {
  if (from == items.size) return this

  val newItems = items.subList(0, from).toMutableList()

  var fromIndex = from
  if (from == 0) {
    newItems.add(items[from].copy(startTime = 0L))
    fromIndex++
  }

  for (i in fromIndex until items.size) {
    newItems.add(items[i].copy(
      startTime = newItems[i-1].run { startTime + duration }
    ))
  }

  return copy(items = newItems)
}