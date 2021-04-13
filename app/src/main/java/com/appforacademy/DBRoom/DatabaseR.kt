package com.appforacademy.DBRoom

import android.content.Context
import androidx.room.RoomDatabase
import com.appforacademy.data.ActorPersistensy
import com.appforacademy.data.GenrePersistensy
import com.appforacademy.data.MoviePersistensy
import androidx.room.Database
import androidx.room.Room



@Database(entities = [MoviePersistensy::class, ActorPersistensy::class,GenrePersistensy::class], version = 1)
abstract class DatabaseR : RoomDatabase(){



    abstract fun DaoInDB():DAO


    companion object {
        private const val DATABASE_NAME = "Films.db"

        fun create(applicationContext: Context): DatabaseR = Room.databaseBuilder(
           applicationContext,
            DatabaseR::class.java,
            DATABASE_NAME
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

    }




}