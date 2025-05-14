package com.nhatnguyenba.habit.data.local

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nhatnguyenba.habit.data.local.dao.HabitDao
import com.nhatnguyenba.habit.data.local.entity.HabitEntity

@Database(
    entities = [HabitEntity::class], // Liệt kê tất cả các Entity
    version = 2, // Phiên bản database
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao // Khai báo DAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Singleton pattern để tránh tạo nhiều instance
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "habit_database" // Tên database
                )
                    .fallbackToDestructiveMigration() // Nếu có thay đổi cấu trúc, xóa database cũ
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}