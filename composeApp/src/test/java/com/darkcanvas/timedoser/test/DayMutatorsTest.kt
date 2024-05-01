package com.darkcanvas.timedoser.test

import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Day
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Task
import org.darkcanvas.timedoser.features.day_player.mutators.addTask
import org.darkcanvas.timedoser.features.day_player.mutators.moveTask
import kotlin.test.Test

class DayMutatorsTest {

  @Test
  fun testMoveTaskFromUpperPosition() {
    val day = Day(
      currentTaskPos = 0,
      items = listOf(
        Task.INITIAL.copy(name = "Task0"),
        Task.INITIAL.copy(name = "Task1"),
        Task.INITIAL.copy(name = "Task2"),
        Task.INITIAL.copy(name = "Task3"),
        Task.INITIAL.copy(name = "Task4"),
      ),
      state = Day.State.WAITING
    )

    val result = day.moveTask(2, 0)

    val answer = day.run {
      copy(items = listOf(
        Task.INITIAL.copy(name = "Task2"),
        Task.INITIAL.copy(name = "Task0"),
        Task.INITIAL.copy(name = "Task1"),
        Task.INITIAL.copy(name = "Task3"),
        Task.INITIAL.copy(name = "Task4"),
      ))
    }
    println(result)

    for (i in 0 until 5) {
      assert(result.items[i].name == answer.items[i].name)
    }

  }

  @Test
  fun testMoveTaskFromLowerPosition() {
    val day = Day(
      currentTaskPos = 0,
      items = listOf(
        Task.INITIAL.copy(name = "Task0"),
        Task.INITIAL.copy(name = "Task1"),
        Task.INITIAL.copy(name = "Task2"),
        Task.INITIAL.copy(name = "Task3"),
        Task.INITIAL.copy(name = "Task4"),
      ),
      state = Day.State.WAITING
    )

    val result = day.moveTask(2, 4)

    val answer = day.run {
      copy(items = listOf(
        Task.INITIAL.copy(name = "Task0"),
        Task.INITIAL.copy(name = "Task1"),
        Task.INITIAL.copy(name = "Task3"),
        Task.INITIAL.copy(name = "Task4"),
        Task.INITIAL.copy(name = "Task2"),
      ))
    }
    println(result)

    for (i in 0 until 5) {
      assert(result.items[i].name == answer.items[i].name)
    }

  }

}