package com.example.greenfinity.data.db.entity


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quest_completions")
data class QuestCompletion(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val questId: Int,
    val questTitle: String,
    val pointsAwarded: Int,
    val completionDate: Long = System.currentTimeMillis()
)