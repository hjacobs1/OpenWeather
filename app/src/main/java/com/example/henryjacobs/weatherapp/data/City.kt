package com.example.henryjacobs.weatherapp.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "city") // table name
data class City(
    @PrimaryKey(autoGenerate = true) var cityId: Long?,
    @ColumnInfo(name = "cityname") var cityName: String
) : Serializable
// must be serializable so that it can be used with Intents later