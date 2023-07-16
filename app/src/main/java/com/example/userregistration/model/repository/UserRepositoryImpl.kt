package com.example.userregistration.model.repository

import android.content.Context
import com.example.userregistration.db.AppDataBase
import com.example.userregistration.db.entity.UserEntity

class UserRepositoryImpl(private val context: Context): UserRepository {
    override suspend fun insertUser(userEntity: UserEntity): Long {
        return AppDataBase.getDB(context).userDao().insert(userEntity)
    }

    override fun userLogin(userName: String, password: String): UserEntity? {
        return AppDataBase.getDB(context).userDao().login(userName, password)
    }
}