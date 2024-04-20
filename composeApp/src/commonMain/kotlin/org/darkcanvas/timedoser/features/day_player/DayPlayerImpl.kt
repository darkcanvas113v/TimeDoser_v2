package org.darkcanvas.timedoser.features.day_player

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import org.darkcanvas.timedoser.core.util.ticker.Ticker
import org.darkcanvas.timedoser.data_domain.day_component.domain.DayRepository
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Day
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Task
import org.darkcanvas.timedoser.features.day_player.events.DayPlayerEvent
import org.darkcanvas.timedoser.features.day_player.mutators.addTask
import org.darkcanvas.timedoser.features.day_player.mutators.editTask
import org.darkcanvas.timedoser.features.day_player.mutators.pause
import org.darkcanvas.timedoser.features.day_player.mutators.progress
import org.darkcanvas.timedoser.features.day_player.mutators.removeTask
import org.darkcanvas.timedoser.features.day_player.mutators.start
import org.darkcanvas.timedoser.features.day_player.mutators.stop
import org.darkcanvas.timedoser.features.day_player.mutators.stopTask

class DayPlayerImpl(
  private val dayRepository: DayRepository,
  private val ticker: Ticker,
  ioScope: CoroutineScope
): DayPlayer {

  init {
    ioScope.launch {
      ticker.observeTicks().collect {
        dayRepository.update { day ->
          day.progress(it) {
            ioScope.launch { _events.emit(it) }
          }
        }
        if (dayRepository.peek().state == Day.State.COMPLETED)
          ticker.stop()
      }
    }
  }

  private val _events = MutableSharedFlow<DayPlayerEvent>()

  override fun play() {
    val day = dayRepository.peek()
    if (day.items.isEmpty()) return

    dayRepository.update {
      it.start()
    }
    ticker.start(1000L)
  }

  override fun stop() {
    dayRepository.update { day ->
      day.stop()
    }
    ticker.stop()
  }

  override fun stopCurrentTask() {
    dayRepository.update { day ->
      day.stopTask()
    }
  }

  override fun pause() {
    dayRepository.update { day ->
      day.pause()
    }
    ticker.stop()
  }

  override fun addTask(task: Task) {
    dayRepository.update { day ->
      day.addTask(task)
    }
  }

  override fun removeTask(taskPos: Int) {
    dayRepository.update { day ->
      day.removeTask(taskPos)
    }
  }

  override fun modifyTask(task: Task, pos: Int) {
    dayRepository.update { day ->
      day.editTask(task, pos)
    }
  }

  override fun observeEvents(): Flow<DayPlayerEvent> = _events
}