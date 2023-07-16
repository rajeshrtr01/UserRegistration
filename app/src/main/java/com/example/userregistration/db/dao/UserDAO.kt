package com.example.userregistration.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.userregistration.db.entity.UserEntity

@Dao
interface UserDAO {

    @Insert
    fun insert(userEntity: UserEntity):Long

    @Query("SELECT * FROM UserEntity WHERE userName= :userName AND password = :password")
    fun login(userName:String, password:String):UserEntity?
}