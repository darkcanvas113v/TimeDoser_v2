package org.darkcanvas.timedoser.data_domain.day_component.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Task(
  val name: String,
  val startTime: Long,
  val duration: Long,
  val state: State,
  val progress: Long
) {
  enum class State {
    WAITING, ACTIVE, COMPLETED, DISABLED
  }

  companion object {
    val INITIAL = Task(
      name = "",
      duration = 0L,
      progress = 0L,
      startTime = 0L,
      state = State.WAITING
    )
  }
}
