package com.leotorrealba.desafiotecnico.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.leotorrealba.desafiotecnico.data.local.AppDatabase
import com.leotorrealba.desafiotecnico.data.local.CartDao
import com.leotorrealba.desafiotecnico.data.remote.api.FakeStoreApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "desafio_tecnico_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideCartDao(appDatabase: AppDatabase): CartDao {
        return appDatabase.cartDao()
    }

    @Provides
    @Singleton
    fun provideFakeStoreApi(): FakeStoreApi {
        return Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FakeStoreApi::class.java)
    }

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application.applicationContext
}