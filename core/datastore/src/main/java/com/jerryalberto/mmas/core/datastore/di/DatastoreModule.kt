package com.jerryalberto.mmas.core.datastore.di

import android.content.Context
import com.jerryalberto.mmas.core.datastore.datasource.SettingPreferenceDataSource
import com.jerryalberto.mmas.core.datastore.datasource.SettingPreferenceDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object  DatastoreModule {

    @Provides
    @Singleton
    fun provideSettingPreferenceDataSource(
        @ApplicationContext context: Context
    ): SettingPreferenceDataSource {
        return SettingPreferenceDataSourceImpl(context)
    }
}