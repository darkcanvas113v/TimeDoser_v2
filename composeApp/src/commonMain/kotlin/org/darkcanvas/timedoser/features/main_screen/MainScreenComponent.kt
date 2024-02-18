package org.darkcanvas.timedoser.features.main_screen

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.Flow
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Day
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Task
import org.darkcanvas.timedoser.features.main_screen.models.DayUIModel
import org.darkcanvas.timedoser.features.task_editor.TaskEditorComponent

interface MainScreenComponent {

  val dayState: Flow<DayUIModel>

  val taskEditorComponent: Value<ChildSlot<*, TaskEditorComponent>>

  fun play()
  fun pause()
  fun stop()
  fun removeTask(atPos: Int)
  fun addTask()
  fun stopCurrentTask()

  fun editTask(pos: Int)

}