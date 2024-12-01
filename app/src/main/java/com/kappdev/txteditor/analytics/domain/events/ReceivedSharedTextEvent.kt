package com.kappdev.txteditor.analytics.domain.events

import com.kappdev.txteditor.analytics.domain.AnalyticsEvent
import com.kappdev.txteditor.analytics.util.AnalyticsConstants

object ReceivedSharedTextEvent : AnalyticsEvent {
    override val eventName: String = AnalyticsConstants.Events.ReceivedSharedText.EVENT
}