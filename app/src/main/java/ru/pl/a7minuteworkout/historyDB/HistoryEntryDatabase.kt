package ru.pl.a7minuteworkout.historyDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [HistoryEntryEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class HistoryEntryDatabase : RoomDatabase() {

    abstract fun historyEntryEntityDao(): HistoryEntryEntityDAO

    companion object {
        @Volatile
        private var INSTANCE: HistoryEntryDatabase? = null

        fun getInstance(context: Context): HistoryEntryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HistoryEntryDatabase::class.java,
                    "history_entry_database"
                ).fallbackToDestructiveMigration()
                    .addTypeConverter(Converters())
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}