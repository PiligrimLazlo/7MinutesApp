package ru.pl.a7minuteworkout.historyDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryEntryEntityDAO {

    @Insert
    suspend fun insert(historyEntryEntity: HistoryEntryEntity)

    @Query("DELETE FROM `history-entity-entry`")
    suspend fun deleteAll()

    @Query("SELECT * FROM `history-entity-entry`")
    fun fetchAllItems(): Flow<List<HistoryEntryEntity>>

    @Query("SELECT * FROM `history-entity-entry` WHERE id=:id")
    fun fetchItemById(id: Int): Flow<HistoryEntryEntity>

}