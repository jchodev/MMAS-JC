package com.jerryalberto.mmas.core.domain.di

import com.jerryalberto.mmas.core.domain.repository.SettingRepository
import com.jerryalberto.mmas.core.domain.repository.TransactionRepository
import com.jerryalberto.mmas.core.domain.usecase.CategoriesUseCase
import com.jerryalberto.mmas.core.domain.usecase.SettingUseCase
import com.jerryalberto.mmas.core.domain.usecase.TransactionUseCase
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
    fun provideGetCategoriesUseCase(): CategoriesUseCase {
        return CategoriesUseCase()
    }

    @Provides
    @Singleton
    fun provideSettingUseCase(settingRepository: SettingRepository): SettingUseCase {
        return SettingUseCase(settingRepository = settingRepository)
    }


    @Provides
    @Singleton
    fun provideTransactionUseCase(
        transactionRepository: TransactionRepository
    ): TransactionUseCase {
        return TransactionUseCase(
            transactionRepository = transactionRepository
        )
    }
}
