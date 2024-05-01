package org.darkcanvas.timedoser.features.main_screen

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable
import org.darkcanvas.timedoser.data_domain.day_component.domain.DayRepository
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Day
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Task
import org.darkcanvas.timedoser.features.day_player.DayPlayer
import org.darkcanvas.timedoser.features.main_screen.models.DayUIModel
import org.darkcanvas.timedoser.features.main_screen.models.toUIModel
import org.darkcanvas.timedoser.features.notification_channel.model.NotificationModel
import org.darkcanvas.timedoser.features.task_editor.TaskEditorComponent
import org.darkcanvas.timedoser.features.task_editor.di.TaskEditorFactoryData
import org.darkcanvas.timedoser.features.task_editor.di.createTaskEditorDI
import org.kodein.di.instance

class DefaultMainScreenComponent(
  componentContext: ComponentContext,
  private val dayPlayer: DayPlayer,
  private val dayRepository: DayRepository
): MainScreenComponent, ComponentContext by componentContext {

  override val dayState: Flow<DayUIModel> = dayRepository.observe().map { it.toUIModel() }

  private val navigation = SlotNavigation<TaskEditorConfig>()

  private fun handleTaskAdded(task: Task, pos: Int) {
    navigation.dismiss()
    if (pos == -1) {
      dayPlayer.addTask(task)
      return
    }

    dayPlayer.modifyTask(task, pos)
  }

  private val taskEditorDI = createTaskEditorDI(
    onDismiss = navigation::dismiss,
    onSuccess = ::handleTaskAdded
    )



  override val taskEditorComponent: Value<ChildSlot<*, TaskEditorComponent>> = childSlot(
    source = navigation,
    serializer = TaskEditorConfig.serializer(),
    handleBackButton = true
  ) { config, childComponentContext ->
    taskEditorDI.instance(arg = TaskEditorFactoryData(
      componentContext = childComponentContext,
      task = config.task,
      pos = config.pos,

    ))
  }

  override fun play() = dayPlayer.play()

  override fun pause() = dayPlayer.pause()

  override fun stop() = dayPlayer.stop()

  override fun removeTask(atPos: Int) = dayPlayer.removeTask(atPos)

  override fun stopCurrentTask() = dayPlayer.stopCurrentTask()

  override fun moveTask(from: Int, to: Int) = dayPlayer.moveTask(from, to)

  override fun addTask() {
    navigation.activate(TaskEditorConfig.NEW_TASK)
  }

  override fun editTask(pos: Int) {
    val task = dayRepository.peek().items[pos]

    if (task.state == Task.State.COMPLETED) return

    navigation.activate(TaskEditorConfig(task = task, pos = pos))
  }

  @Serializable
  private data class TaskEditorConfig(
    val task: Task,
    val pos: Int
  ) {
    companion object {
      val NEW_TASK = TaskEditorConfig(
        task = Task.INITIAL,
        pos = -1
      )
    }
  }


}