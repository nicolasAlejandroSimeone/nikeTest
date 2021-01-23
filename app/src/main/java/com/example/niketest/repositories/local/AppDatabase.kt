package com.example.niketest.repositories.local


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.niketest.models.Results

@Database(entities = [Results::class], version = 3)
abstract class AppDatabase: RoomDatabase() {
    abstract fun searchedWordDAO() : SearchedWordDAO

    companion object{
        @Volatile
        private var INSTANCE:AppDatabase? = null
        private const val ROOM_DB_NAME = "searchWord.db"

        fun getDatabase(context: Context): AppDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    ROOM_DB_NAME
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}