package com.kappdev.txteditor.analytics.domain.properties

import com.kappdev.txteditor.analytics.domain.AnalyticsProperty
import com.kappdev.txteditor.analytics.util.AnalyticsConstants

class TextSizeProperty(textSize: Int): AnalyticsProperty {

    override val propertyName: String = AnalyticsConstants.UserProperties.TEXT_SIZE

    override val parameter: String = textSize.toString()
}