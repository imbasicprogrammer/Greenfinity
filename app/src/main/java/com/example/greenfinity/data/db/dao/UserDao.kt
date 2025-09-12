package com.example.greenfinity.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.greenfinity.data.db.entity.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM user_account LIMIT 1")
    fun getCurrentUser(): Flow<User?>

    @Query("DELETE FROM user_account")
    suspend fun clearUser()

    @Update
    suspend fun updateUser(user: User)


    @Query("SELECT * FROM user_account WHERE (LOWER(email) = LOWER(TRIM(:identifier)) OR LOWER(username) = LOWER(TRIM(:identifier))) AND password = :password LIMIT 1")
    suspend fun getUserByCredentials(identifier: String, password: String): User?

    @Query("SELECT * FROM user_account WHERE email = :identifier OR username = :identifier LIMIT 1")
    suspend fun findUserByIdentifier(identifier: String): User?
}