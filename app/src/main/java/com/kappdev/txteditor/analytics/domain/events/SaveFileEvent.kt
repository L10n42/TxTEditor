package com.kappdev.txteditor.analytics.domain.events

import com.kappdev.txteditor.analytics.domain.AnalyticsEvent
import com.kappdev.txteditor.analytics.util.AnalyticsConstants

object SaveFileEvent : AnalyticsEvent {
    override val eventName: String = AnalyticsConstants.Events.SaveFile.EVENT
}