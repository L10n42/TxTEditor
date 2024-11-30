package com.kappdev.txteditor.analytics.domain.properties

import com.kappdev.txteditor.analytics.domain.AnalyticsProperty
import com.kappdev.txteditor.analytics.util.AnalyticsConstants

class LineNumberingProperty(showLineNumbering: Boolean): AnalyticsProperty {

    override val propertyName: String = AnalyticsConstants.UserProperties.LINE_NUMBERING

    override val parameter: String = if (showLineNumbering) "on" else "off"
}