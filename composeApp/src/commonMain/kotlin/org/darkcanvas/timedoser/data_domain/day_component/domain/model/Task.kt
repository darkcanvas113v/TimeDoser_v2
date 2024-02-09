package org.darkcanvas.timedoser.data_domain.day_component.domain.model

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
}
