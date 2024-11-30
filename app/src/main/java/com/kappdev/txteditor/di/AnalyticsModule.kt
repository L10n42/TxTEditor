package com.kappdev.txteditor.di

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import com.kappdev.txteditor.analytics.data.AnalyticsSenderImpl
import com.kappdev.txteditor.analytics.domain.AnalyticsSender
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AnalyticsModule {

    @Provides
    fun provideFirebaseAnalytics(@ApplicationContext context: Context): FirebaseAnalytics {
        return FirebaseAnalytics.getInstance(context)
    }

    @Provides
    fun provideAnalyticsSender(firebaseAnalytics: FirebaseAnalytics): AnalyticsSender {
        return AnalyticsSenderImpl(firebaseAnalytics)
    }
}