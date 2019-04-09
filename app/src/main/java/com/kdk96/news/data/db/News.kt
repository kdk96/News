package com.kdk96.news.data.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

typealias NewsEntity = News

@Entity(tableName = "NEWS")
data class News(
    @PrimaryKey val url: String,
    val title: String,
    @ColumnInfo(name = "image_url") val imageUrl: String?,
    val description: String,
    val date: String
)