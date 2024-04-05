package com.jerryalberto.mmas.core.data.di

import com.jerryalberto.mmas.core.data.repository.SettingPreferenceRepositoryImpl
import com.jerryalberto.mmas.core.data.repository.TransactionRepositoryImpl
import com.jerryalberto.mmas.core.domain.repository.SettingPreferenceRepository
import com.jerryalberto.mmas.core.domain.repository.TransactionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryAbstractModule {
    @Binds
    @Singleton
    abstract fun bindTransactionRepository(transactionRepository: TransactionRepositoryImpl): TransactionRepository

  @Binds
    @Singleton
    abstract fun bindSettingPreferenceRepository(settingPreferenceRepository: SettingPreferenceRepositoryImpl): SettingPreferenceRepository

}