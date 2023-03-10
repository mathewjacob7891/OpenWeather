package com.mathew.openweather.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class City(
    val cityName: String?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
