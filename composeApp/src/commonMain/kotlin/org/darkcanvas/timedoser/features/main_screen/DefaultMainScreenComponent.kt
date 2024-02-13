package org.darkcanvas.timedoser.features.main_screen

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.Flow
import org.darkcanvas.timedoser.data_domain.day_component.domain.DayRepository
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Day
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Task
import org.darkcanvas.timedoser.features.day_player.DayPlayer

class DefaultMainScreenComponent(
  componentContext: ComponentContext,
  private val dayPlayer: DayPlayer,
  dayRepository: DayRepository
): MainScreenComponent, ComponentContext by componentContext {

  override val dayState: Flow<Day> = dayRepository.observe()

  override fun play() = dayPlayer.play()

  override fun pause() = dayPlayer.pause()

  override fun stop() = dayPlayer.stop()

  override fun removeTask(atPos: Int) = dayPlayer.removeTask(atPos)

  override fun addTask(task: Task) = dayPlayer.addTask(task)

  override fun stopCurrentTask() = dayPlayer.stopCurrentTask()


}