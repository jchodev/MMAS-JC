package com.jerryalberto.mmas.core.ui.di

import com.jerryalberto.mmas.core.ui.helper.UiHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal object UiModule {

    @Provides
    @Singleton
    fun provideCategoryHelper(): UiHelper {
        return UiHelper()
    }
}