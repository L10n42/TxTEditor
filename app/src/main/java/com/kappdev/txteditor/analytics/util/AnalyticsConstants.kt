package com.kappdev.txteditor.analytics.util

object AnalyticsConstants {

    object Events {

        object ScreenView {
            const val EVENT = "screen_view"

            object Params {
                const val SCREEN_NAME = "screen_name"
                const val SCREEN_CLASS = "screen_class"
            }
        }

        object CopyText {
            const val EVENT = "copy_text"
        }

        object ShareText {
            const val EVENT = "share_text"
        }

        object OpenFile {
            const val EVENT = "open_file"
        }

        object SaveFile {
            const val EVENT = "save_file"
        }

        object NewFile {
            const val EVENT = "new_file"
        }

        object OpenFileFromHistory {
            const val EVENT = "open_file_from_history"
        }

        object ClearHistory {
            const val EVENT = "clear_history"
        }

    }

    object UserProperties {
        const val APP_THEME = "app_theme"
        const val LINE_NUMBERING = "line_numbering"
        const val TEXT_STYLE = "text_style"
        const val TEXT_SIZE = "text_size"
    }
}