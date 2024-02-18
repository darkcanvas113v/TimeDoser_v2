package org.darkcanvas.timedoser.core.util

import kotlin.math.abs

fun convertToMillis(hours: Int, minutes: Int, seconds: Int = 0): Long {
  return ((hours*60L + minutes)*60 + seconds)*1000L
}

fun getHoursAndMinutes(timeInMillis: Long): Pair<Int, Int> {
  val overallMinutes = (timeInMillis / 60000).toInt()
  val minutes = overallMinutes % 60
  val overallHours = overallMinutes / 60
  val hours = overallHours % 24

  return Pair(hours, minutes)
}

fun convertMillisToStringFormat(millis: Long): String {
  val overallMinutes = abs(millis) / 60000
  val minutes = overallMinutes % 60
  val overallHours = overallMinutes / 60
  val hours = overallHours % 24

  return formatIfNeeded(hours.toInt(), minutes.toInt())
}

fun convertMillisToStringFormatWithSeconds(millis: Long): String {
  val overallSeconds = millis / 1000
  val seconds = overallSeconds % 60

  val overallMinutes = overallSeconds / 60
  val minutes = overallMinutes % 60

  val overallHours = overallMinutes / 60
  val hours = overallHours % 24

  var formattedSeconds = ":$seconds"
  if (seconds < 10)
    formattedSeconds = ":0$seconds"

  return formatIfNeeded(hours.toInt(), minutes.toInt(), formattedSeconds)
}

fun formatIfNeeded(hours: Int, minutes: Int, secondsFormatted: String = ""): String {
  var stringHours = hours.toString()
  var stringMinutes = minutes.toString()

  if (hours < 10) {
    stringHours = "0$hours"
  }
  if (minutes < 10) {
    stringMinutes = "0$minutes"
  }
  return "$stringHours:$stringMinutes$secondsFormatted"
}

fun formatValue(value: Int): String {
  if (value < 10) {
    return "0$value"
  }
  return "$value"
}