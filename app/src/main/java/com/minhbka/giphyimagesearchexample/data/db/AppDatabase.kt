package com.minhbka.giphyimagesearchexample.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.minhbka.giphyimagesearchexample.data.entities.GiphyImage

@Database(
    entities = [GiphyImage::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getFavorGiphyImageDao():FavorGiphyImageDao

    companion object{
        @Volatile
        private var instance:AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {

                instance = it
            }
        }
        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "MyAppDatabase.db"
        ).build()
    }
}