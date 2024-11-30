package com.kappdev.txteditor.analytics.data

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.kappdev.txteditor.analytics.domain.AnalyticsEvent
import com.kappdev.txteditor.analytics.domain.AnalyticsProperty
import com.kappdev.txteditor.analytics.domain.AnalyticsSender
import javax.inject.Inject

class AnalyticsSenderImpl @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics
) : AnalyticsSender {

    override fun sendEvent(event: AnalyticsEvent) {
        val paramsBundle = Bundle().apply {
            event.params.forEach { (key, value) ->
                when (value) {
                    is String -> putString(key, value)
                    is Int -> putInt(key, value)
                    is Double -> putDouble(key, value)
                    else -> throw IllegalArgumentException("Unsupported type: ${value::class.java}")
                }
            }
        }
        firebaseAnalytics.logEvent(event.eventName, paramsBundle)
    }

    override fun setUserProperty(property: AnalyticsProperty) {
        firebaseAnalytics.setUserProperty(property.propertyName, property.parameter)
    }

}