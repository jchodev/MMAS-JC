package com.jerryalberto.mmas.feature.home.di

import com.jerryalberto.mmas.feature.home.helper.CategoryHelper
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
    fun provideCategoryHelper(): CategoryHelper {
        return CategoryHelper()
    }
}