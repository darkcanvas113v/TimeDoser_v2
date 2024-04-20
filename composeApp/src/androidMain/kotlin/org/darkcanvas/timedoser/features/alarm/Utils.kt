package org.darkcanvas.timedoser.features.alarm

import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.activity.ComponentActivity

fun getDefaultVibrator(context: Context): Vibrator {
  return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
    (context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager).defaultVibrator
  } else {
    context.getSystemService(ComponentActivity.VIBRATOR_SERVICE) as Vibrator
  }
}

fun Vibrator.vibrateCompat(duration: Long) {
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    vibrate(
      VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE)
    )
  } else {
    @Suppress("DEPRECATION")
    vibrate(duration)
  }
}


fun getDefaultRingtone(context: Context): Ringtone {
  val soundUri =
    RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_ALARM)

  return RingtoneManager.getRingtone(context, soundUri)
}
