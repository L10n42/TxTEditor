package com.kappdev.txteditor.analytics.domain.properties

import com.kappdev.txteditor.analytics.domain.AnalyticsProperty
import com.kappdev.txteditor.analytics.util.AnalyticsConstants
import com.kappdev.txteditor.editor_feature.presentation.editor.TextStyle

class TextStyleProperty(textStyle: TextStyle): AnalyticsProperty {

    override val propertyName: String = AnalyticsConstants.UserProperties.TEXT_STYLE

    override val parameter: String = textStyle.name
}