package com.example.userregistration.db

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.userregistration.db.dao.UserDAO
import com.example.userregistration.db.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDataBase: RoomDatabase() {

    abstract fun userDao ():UserDAO

    companion object{
        var INSTANCE:AppDataBase? = null

        fun getDB(context:Context):AppDataBase{
            if (INSTANCE==null){

                synchronized(this){
                    val instance = Room
                        .databaseBuilder(context.applicationContext, AppDataBase::class.java, "user_db")
                        .build()
                    INSTANCE = instance
                }

            }
            return  INSTANCE!!
        }
    }
}