package com.example.greenfinity.data.db.dao



import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.greenfinity.data.db.entity.QuestCompletion
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestCompletionDao {
    @Insert
    suspend fun insert(completion: QuestCompletion)


    @Query("SELECT * FROM quest_completions ORDER BY completionDate DESC LIMIT 3")
    fun getRecentCompletions(): Flow<List<QuestCompletion>>

    @Query("SELECT * FROM quest_completions ORDER BY completionDate DESC")
    fun getAllCompletions(): Flow<List<QuestCompletion>>
}