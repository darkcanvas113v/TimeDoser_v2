package org.darkcanvas.timedoser.features.day_player

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import org.darkcanvas.timedoser.core.util.ticker.Ticker
import org.darkcanvas.timedoser.data_domain.day_component.domain.DayRepository
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Day
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Task
import org.darkcanvas.timedoser.features.day_player.model_mutators.addTask
import org.darkcanvas.timedoser.features.day_player.model_mutators.pause
import org.darkcanvas.timedoser.features.day_player.model_mutators.progress
import org.darkcanvas.timedoser.features.day_player.model_mutators.start
import org.darkcanvas.timedoser.features.day_player.model_mutators.stop
import org.darkcanvas.timedoser.features.day_player.model_mutators.stopTask

class DayPlayerImpl(
  private val dayRepository: DayRepository,
  private val ticker: Ticker,
  private val ioScope: CoroutineScope
): DayPlayer {

  init {
    ioScope.launch {
      ticker.observeTicks().collect {
        dayRepository.update { day ->
          day.progress(it)
        }
        if (dayRepository.observe().last().state == Day.State.COMPLETED)
          ticker.stop()
      }
    }
  }

  override fun play() {
    dayRepository.update { day ->
      day.start()
    }
    ticker.start(500L)
  }

  override fun stop() {
    dayRepository.update { day ->
      day.stop()
    }
    ticker.stop()
  }

  override fun stopTask() {
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

  }
}