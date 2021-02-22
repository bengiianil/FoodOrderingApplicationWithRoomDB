package com.example.yemeksiparisuygulamasi.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.yemeksiparisuygulamasi.model.FoodRoom


@Database(entities = [FoodRoom::class],version = 1)
abstract class FoodDatabase : RoomDatabase() {

    abstract fun foodDao() : FoodDao

    //Singleton
    companion object {

        @Volatile private var instance : FoodDatabase? = null

        private val lock = Any()

        operator fun invoke(context : Context) = instance ?: synchronized(lock) {
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }


        private fun makeDatabase(context : Context) = Room.databaseBuilder(
            context.applicationContext,FoodDatabase::class.java,"fooddatabase"
        ).build()
    }
}