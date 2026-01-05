package com.example.worldfavorites.di

import android.content.Context
import androidx.room.Room
import com.example.worldfavorites.data.local.FavoriteCountryDao
import com.example.worldfavorites.data.local.WorldFavoritesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideWorldFavoritesDatabase(
        @ApplicationContext context: Context
    ): WorldFavoritesDatabase {
        return Room.databaseBuilder(
            context,
            WorldFavoritesDatabase::class.java,
            "world_favorites_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFavoriteCountryDao(database: WorldFavoritesDatabase): FavoriteCountryDao {
        return database.favoriteCountryDao()
    }
}
