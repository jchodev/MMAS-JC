package com.jerryalberto.mmas.core.domain.di

import com.jerryalberto.mmas.core.domain.usecase.GetCategoriesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetCategoriesUseCase(): GetCategoriesUseCase {
        return GetCategoriesUseCase()
    }
}
