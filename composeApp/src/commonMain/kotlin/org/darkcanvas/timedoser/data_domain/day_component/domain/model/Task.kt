package org.darkcanvas.timedoser.data_domain.day_component.domain.model

import com.squareup.moshi.JsonClass
import kotlinx.serialization.Serializable

@JsonClass(generateAdapter = true)
@Serializable
data class Task(
  val name: String,
  val startTime: Long,
  val duration: Long,
  val state: State,
  val progress: Long
) {
  enum class State {
    WAITING, PAUSED, ACTIVE, COMPLETED, DISABLED
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
