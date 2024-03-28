package com.jerryalberto.mmas.feature.home.di

import com.jerryalberto.mmas.core.ui.helper.UiHelper
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
    fun provideCategoryHelper(): com.jerryalberto.mmas.core.ui.helper.UiHelper {
        return com.jerryalberto.mmas.core.ui.helper.UiHelper()
    }
}