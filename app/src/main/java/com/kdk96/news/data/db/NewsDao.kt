package com.kdk96.news.data.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Single

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(news: List<News>)

    @Query("DELETE FROM NEWS")
    fun deleteAll()

    @Query("SELECT * FROM NEWS")
    fun getNews(): Single<List<News>>
}