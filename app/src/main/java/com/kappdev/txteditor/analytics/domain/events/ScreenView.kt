package com.kappdev.txteditor.analytics.domain.events

import com.kappdev.txteditor.analytics.domain.AnalyticsEvent
import com.kappdev.txteditor.analytics.util.AnalyticsConstants

class ScreenView(screenName: String): AnalyticsEvent {

    override val eventName: String = AnalyticsConstants.Events.ScreenView.EVENT

    override val params: Map<String, Any> = mapOf(
        AnalyticsConstants.Events.ScreenView.Params.SCREEN_NAME to screenName,
        AnalyticsConstants.Events.ScreenView.Params.SCREEN_CLASS to screenName
    )
}
