package com.example.greenfinity.data.db.entity




import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_account")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val username: String,
    val email: String,
    val password: String,
    val points: Int = 0,
    val level: Int = 1,
    val avatarResName: String? = null

)