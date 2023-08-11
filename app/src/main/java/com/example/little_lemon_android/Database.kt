package com.example.little_lemon_android

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Entity
data class MenuItemDatabase(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val image: String,
    val category: String,
)

@Dao
interface MenuDao {
    @Query("SELECT * FROM MenuItemDatabase")
    fun getAll(): LiveData<List<MenuItemDatabase>>

    @Insert
    fun insertAll(vararg menuItems: MenuItemDatabase)

    @Query("SELECT (SELECT COUNT(*) FROM MenuItemDatabase) == 0")
    fun isEmpty(): Boolean
}

@Database(entities = [MenuItemDatabase::class], version = 1)
abstract class MenuDatabase: RoomDatabase() {
    abstract fun menuDao(): MenuDao
}