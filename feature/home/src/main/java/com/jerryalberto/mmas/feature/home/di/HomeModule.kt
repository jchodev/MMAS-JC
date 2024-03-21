package com.jerryalberto.mmas.feature.home.di

import com.jerryalberto.mmas.feature.home.ui.helper.UiHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object HomeModule {

    @Provides
    @Singleton
    fun provideCategoryHelper(): UiHelper {
        return UiHelper()
    }
}