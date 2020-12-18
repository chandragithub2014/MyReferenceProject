package com.ref.roomdboprs.database

import androidx.room.*
import com.ref.roomdboprs.model.User
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {

    @Query("SELECT * FROM user_bills WHERE user_name = :userId")
    fun fetchByUserId(userId: String): Flow<List<User>>

    @Query("SELECT * FROM user_bills WHERE uid = :uId")
    fun fetchByUId(uId: Long): Flow<User>

    @Insert
    fun insertAll(vararg users: User)

    @Query("DELETE from user_bills where uid = :uid")
    suspend fun delete(uid: Long) : Int


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User) : Long

    @Update
    suspend fun updateUser(user: User) : Int

    @Query("SELECT SUM(visit_expense) FROM user_bills WHERE user_name = :userId")
    fun fetchSumOfExpenses(userId: String):Flow<Long>
}