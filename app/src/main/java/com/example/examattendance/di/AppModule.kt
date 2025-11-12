package com.example.examattendance.di

import android.content.Context
import androidx.room.Room
import com.example.examattendance.data.api.ExamsApi
import com.example.examattendance.data.db.AppDatabase
import com.example.examattendance.data.repository.ExamsRepository
import com.example.examattendance.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): ExamsApi = retrofit.create(ExamsApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "admitqr.db").build()

    @Provides
    @Singleton
    fun provideRepository(api: ExamsApi, db: AppDatabase): ExamsRepository =
        ExamsRepository(api, db)
}
