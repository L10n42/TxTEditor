package com.kappdev.txteditor.analytics.domain

interface AnalyticsSender {

    fun sendEvent(event: AnalyticsEvent)

    fun setUserProperty(property: AnalyticsProperty)

}