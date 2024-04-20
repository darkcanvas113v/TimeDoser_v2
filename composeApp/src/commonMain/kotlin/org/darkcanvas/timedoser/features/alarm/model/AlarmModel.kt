package org.darkcanvas.timedoser.features.alarm.model

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import org.darkcanvas.timedoser.features.day_player.events.DayPlayerEvent

@JsonClass(generateAdapter = true)
data class AlarmModel(
  val currentTaskName: String,
  val nextTaskName: String?
) {
  fun toJson(): String = adapter.toJson(this)

  companion object {
    const val EXTRA_ID = "ALARM_MODEL"
    private val adapter: JsonAdapter<AlarmModel> = Moshi.Builder().build().adapter(AlarmModel::class.java)

    fun fromJson(json: String) = adapter.fromJson(json)
  }
}

fun DayPlayerEvent.TaskEnded.toAlarmModel() = AlarmModel(
  currentTaskName = task.name,
  nextTaskName = nextTask?.name
)
