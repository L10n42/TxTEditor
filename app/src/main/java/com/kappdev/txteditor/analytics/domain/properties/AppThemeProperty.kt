package com.kappdev.txteditor.analytics.domain.properties

import com.kappdev.txteditor.analytics.domain.AnalyticsProperty
import com.kappdev.txteditor.analytics.util.AnalyticsConstants

class AppThemeProperty(isDarkTheme: Boolean) : AnalyticsProperty {

    override val propertyName: String = AnalyticsConstants.UserProperties.APP_THEME

    override val parameter: String = if (isDarkTheme) "dark" else "light"
}