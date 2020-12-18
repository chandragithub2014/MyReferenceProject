package com.ref.roomdboprs.dependency

import android.content.Context
import com.ref.roomdboprs.database.UserInfoDataBase
import com.ref.roomdboprs.repository.UserDBRepository


object DependencyUtils {
    lateinit var context : Context

    private fun provideDBInstance() = UserInfoDataBase.getInstance(context).userDao()

    fun fetchUserRepository(): UserDBRepository {
        return  UserDBRepository(provideDBInstance())

    }

    fun setAppContext(context: Context){
        this.context = context
    }

}