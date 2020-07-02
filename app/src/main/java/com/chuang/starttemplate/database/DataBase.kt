package com.chuang.starttemplate.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.chuang.fridgetracker.database.Converter
import com.chuang.fridgetracker.database.ItemDao
import com.chuang.starttemplate.database.data.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

@Database(entities = [Item::class], version = 1)
@TypeConverters(Converter::class)
abstract class DataBase : RoomDatabase() {
    abstract fun itemDao(): ItemDao

    companion object {
        @Volatile
        private var INSTANCE: DataBase? = null

        fun getDatabase(context: Context): DataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBase::class.java,
                    "TodoRenameThis.db"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class TodoDatabaseCallback(private val scope: CoroutineScope) :
            RoomDatabase.Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.itemDao())
                    }
                }
            }
        }

        // Add test data
        suspend fun populateDatabase(itemDao: ItemDao) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            itemDao.deleteAll()

            val item = Item("test", "test Date", Date(), Date(), 1)
            itemDao.insertAll(item)
        }
    }
}