package com.kappdev.txteditor.analytics.domain

interface AnalyticsEvent {
    val eventName: String

    val params: Map<String, Any>
        get() = mapOf()
}