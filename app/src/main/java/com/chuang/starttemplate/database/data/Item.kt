package com.chuang.starttemplate.database.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.chuang.fridgetracker.database.Converter
import java.io.Serializable
import java.util.*

@TypeConverters(Converter::class)
@Entity
data class Item(
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "category") var category: String,
    @ColumnInfo(name = "get_date") var firstDate: Date,
    @ColumnInfo(name = "expiry_date") var expiryDate: Date,
    @ColumnInfo(name = "quantity") var quantity: Int
) : Serializable, Comparable<Item> {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    override fun compareTo(other: Item): Int {
        return if (this.quantity > 0 && other.quantity == 0) {
            1
        } else if (this.quantity == 0 && other.quantity > 0) {
            -1
        } else {
            (other.expiryDate.time - this.expiryDate.time).toInt()
        }
    }
}