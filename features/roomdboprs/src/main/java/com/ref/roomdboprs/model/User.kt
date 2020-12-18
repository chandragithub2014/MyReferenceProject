package com.ref.roomdboprs.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_bills")
data class User(
    @PrimaryKey(autoGenerate = true) val uid: Long,
    @ColumnInfo(name = "user_name") val userName: String?,
    @ColumnInfo(name = "place_visited") val placeVisited: String?,
    @ColumnInfo(name = "visit_purpose") val visitPurpose: String?,
    @ColumnInfo(name = "visit_expense") val visitExpense: Long?,
    @ColumnInfo(name = "visited_date") val visitedDate: String?

)