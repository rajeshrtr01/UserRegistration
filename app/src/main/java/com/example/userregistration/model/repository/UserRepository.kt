package com.example.userregistration.model.repository

import com.example.userregistration.db.entity.UserEntity

interface UserRepository {
    suspend fun insertUser(userEntity: UserEntity):Long

    fun userLogin(userName:String, password:String):UserEntity?
}