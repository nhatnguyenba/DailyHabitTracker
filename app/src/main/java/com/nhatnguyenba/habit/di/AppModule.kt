package com.nhatnguyenba.habit.di

import android.content.Context
import com.nhatnguyenba.habit.data.local.AppDatabase
import com.nhatnguyenba.habit.data.local.dao.HabitDao
import com.nhatnguyenba.habit.data.mapper.HabitMapper
import com.nhatnguyenba.habit.data.repository.HabitRepositoryImpl
import com.nhatnguyenba.habit.domain.repository.HabitRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideHabitDao(db: AppDatabase): HabitDao = db.habitDao()

    @Provides
    @Singleton
    fun provideHabitRepository(dao: HabitDao): HabitRepository =
        HabitRepositoryImpl(dao, HabitMapper())
}