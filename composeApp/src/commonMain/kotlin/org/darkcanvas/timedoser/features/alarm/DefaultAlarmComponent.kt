package org.darkcanvas.timedoser.features.alarm

import com.arkivanov.decompose.ComponentContext
import org.darkcanvas.timedoser.features.alarm.model.AlarmUIModel

class DefaultAlarmComponent(
  componentContext: ComponentContext,
  override val alarmUIModel: AlarmUIModel
) : AlarmComponent, ComponentContext by componentContext {

}