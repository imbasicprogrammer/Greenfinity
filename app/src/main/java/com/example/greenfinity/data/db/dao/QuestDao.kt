package com.example.greenfinity.data.db.dao



import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.greenfinity.data.db.entity.Quest
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(quests: List<Quest>)

    @Query("SELECT * FROM quests")
    fun getAllQuests(): Flow<List<Quest>>

    @Query("SELECT * FROM quests WHERE id = :questId")
    suspend fun getQuestById(questId: Int): Quest?


}