package ru.pl.a7minuteworkout.historyDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "history-entity-entry")
data class HistoryEntryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "finish-date-time")
    val finishDate: Date = Date()
)