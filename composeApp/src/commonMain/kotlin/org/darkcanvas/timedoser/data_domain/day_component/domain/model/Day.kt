package org.darkcanvas.timedoser.data_domain.day_component.domain.model

data class Day(
  val state: State,
  val currentTaskPos: Int,
  val items: List<Task>
) {
  enum class State {
    ACTIVE, WAITING, COMPLETED, DISABLED
  }

  companion object {
    val DEFAULT = Day(
      state = State.WAITING,
      currentTaskPos = 0,
      items = emptyList()
    )
  }
}
