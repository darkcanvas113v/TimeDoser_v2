package org.darkcanvas.timedoser.core

import android.app.PendingIntent
import android.os.Build

fun getImmutablePendingIntentFlags(): Int {
  var piFlags = PendingIntent.FLAG_UPDATE_CURRENT
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    piFlags = piFlags or PendingIntent.FLAG_IMMUTABLE
  }
  return piFlags
}