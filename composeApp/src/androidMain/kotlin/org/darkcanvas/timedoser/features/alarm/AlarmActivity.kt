package org.darkcanvas.timedoser.features.alarm

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import org.darkcanvas.timedoser.core.getImmutablePendingIntentFlags
import org.darkcanvas.timedoser.core.theme.DefaultTheme
import org.darkcanvas.timedoser.features.alarm.model.AlarmModel
import org.darkcanvas.timedoser.features.alarm.model.AlarmUIModel
import org.darkcanvas.timedoser.features.alarm.model.toAlarmModel
import org.darkcanvas.timedoser.features.alarm.model.toAlarmUIModel
import org.darkcanvas.timedoser.features.alarm.ui.AlarmScreen

class AlarmActivity: ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val model = intent.getStringExtra(AlarmModel.EXTRA_ID)?.let { AlarmModel.fromJson(it) }

    if (model == null) {
      finish()
      return
    }

    val component = DefaultAlarmComponent(
      componentContext = defaultComponentContext(),
      alarmUIModel = model.toAlarmUIModel()
    )

    setContent {
      DefaultTheme {
        AlarmScreen(
          component = component,
          disableAlarm = {
            startService(AlarmService.getDisableAlarmIntent(this))
            finish()
          }
        )
      }
    }
  }

  companion object {
    fun getIntent(context: Context, alarmModel: AlarmModel): PendingIntent {
      val intent = Intent(context, AlarmActivity::class.java)
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
      intent.putExtra(AlarmModel.EXTRA_ID, alarmModel.toJson())
      return PendingIntent.getActivity(context, 0, intent, getImmutablePendingIntentFlags())
    }
  }
}