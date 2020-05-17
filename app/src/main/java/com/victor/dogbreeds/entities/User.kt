package com.victor.dogbreeds.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_user")
data class User (
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "birthdate") var birthdate: String,
    @ColumnInfo(name = "email") var email: String,
    @ColumnInfo(name = "fullname") var fullname: String
)