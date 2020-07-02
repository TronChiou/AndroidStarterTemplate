package com.chuang.fridgetracker.database

import androidx.annotation.WorkerThread
import com.chuang.starttemplate.database.data.Item

class ItemRepository(private val itemDao: ItemDao) {
    val allItem: List<Item> = itemDao.getAll()

    @WorkerThread
    suspend fun insert(item: Item) {
        itemDao.insertAll(item)
    }

    @WorkerThread
    suspend fun update(item: Item) {
        itemDao.update(item)
    }

    @WorkerThread
    suspend fun delete(item: Item) {
        itemDao.delete(item)
    }
}