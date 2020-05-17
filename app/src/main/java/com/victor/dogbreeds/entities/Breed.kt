package com.victor.dogbreeds.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_breeds")
data class Breed (
    @PrimaryKey @ColumnInfo(name = "displayName") var displayName: String,
    @ColumnInfo(name = "masterBreed") var masterBreed: String,
    @ColumnInfo(name = "subBreed") var subBreed: String,
    @ColumnInfo(name = "favorite") var favorite: Boolean
)