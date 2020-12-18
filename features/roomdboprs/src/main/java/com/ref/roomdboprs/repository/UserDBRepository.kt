package com.ref.roomdboprs.repository

import com.ref.roomdboprs.database.UserDao
import com.ref.roomdboprs.model.User
import kotlinx.coroutines.flow.Flow


class UserDBRepository(private val userDao: UserDao) {

    fun fetchUserBillDetails(userName : String): Flow<List<User>> = userDao.fetchByUserId(userName)
    fun fetchUserBillDetailsByUID(uid : Long) :  Flow<User> = userDao.fetchByUId(uid)
    suspend fun insertUserInfo(user: User) : Long = userDao.insert(user)

    suspend fun deleteUserInfo(uid : Long): Int  =  userDao.delete(uid)

    suspend fun updateUser(user:User) : Int = userDao.updateUser(user)

    fun fetchTotalExpenses(userName : String) : Flow<Long> = userDao.fetchSumOfExpenses(userName)
}