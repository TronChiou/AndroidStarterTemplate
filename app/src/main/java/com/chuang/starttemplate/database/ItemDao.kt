package com.chuang.fridgetracker.database

import androidx.room.*
import com.chuang.starttemplate.database.data.Item

@Dao
interface ItemDao {
    @Query("SELECT * FROM item")
    fun getAll(): List<Item>

    @Query("SELECT * FROM item WHERE id = (:itemID)")
    fun findById(itemID: Int): Item

    @Insert()
    suspend fun insertAll(vararg item: Item)

    @Update
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)

    @Query("DELETE FROM Item")
    fun deleteAll()
}