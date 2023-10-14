package com.kappdev.txteditor.di

import android.app.Application
import com.kappdev.txteditor.data.SettingsManager
import com.kappdev.txteditor.data.repository.HistoryRepositoryImpl
import com.kappdev.txteditor.domain.repository.HistoryRepository
import com.kappdev.txteditor.domain.use_case.AddToHistory
import com.kappdev.txteditor.domain.use_case.GetFileName
import com.kappdev.txteditor.domain.use_case.ReadFile
import com.kappdev.txteditor.domain.use_case.ShareText
import com.kappdev.txteditor.domain.use_case.WriteFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideReadFileUseCase(app: Application): ReadFile = ReadFile(app)

    @Provides
    @Singleton
    fun provideWriteFileUseCase(app: Application): WriteFile = WriteFile(app)

    @Provides
    @Singleton
    fun provideSettingsManager(app: Application): SettingsManager = SettingsManager(app)

    @Provides
    @Singleton
    fun provideShareTextUseCase(app: Application): ShareText = ShareText(app)

    @Provides
    @Singleton
    fun provideGetFileNameUseCase(app: Application): GetFileName = GetFileName(app)

    @Provides
    @Singleton
    fun provideAddToHistoryUseCase(rep: HistoryRepository): AddToHistory = AddToHistory(rep)

    @Provides
    @Singleton
    fun provideHistoryRepository(app: Application): HistoryRepository = HistoryRepositoryImpl(app)
}