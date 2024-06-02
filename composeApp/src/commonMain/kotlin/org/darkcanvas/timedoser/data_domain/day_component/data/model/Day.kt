package org.darkcanvas.timedoser.data_domain.day_component.data.model

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Day
import org.darkcanvas.timedoser.features.alarm.model.AlarmModel

private val adapter: JsonAdapter<Day> = Moshi.Builder().build().adapter(Day::class.java)
fun Day.toJson(): String = adapter.toJson(this)
fun dayFromJson(data: String): Day = try {
  adapter.fromJson(data) ?: Day.DEFAULT
} catch (e: JsonDataException) {
  Day.DEFAULT
}