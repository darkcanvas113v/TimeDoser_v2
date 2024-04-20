package org.darkcanvas.timedoser.features.alarm.model

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import org.darkcanvas.timedoser.features.day_player.events.DayPlayerEvent

@JsonClass(generateAdapter = true)
data class AlarmUIModel(
  val currentTaskText: AnnotatedString,
  val nextTaskText: AnnotatedString?
)

fun AlarmModel.toAlarmUIModel() = AlarmUIModel(
  currentTaskText = buildAnnotatedString {
    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
      append(currentTaskName)
    }
    append("'s time is up")
  },
  nextTaskText = nextTaskName?.let {
    buildAnnotatedString {
      append("Next task is")
      withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
        append(it)
      }
    }
  }
)
