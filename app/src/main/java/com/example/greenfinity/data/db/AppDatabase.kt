package com.example.greenfinity.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.greenfinity.data.db.dao.QuestCompletionDao
import com.example.greenfinity.data.db.dao.QuestDao
import com.example.greenfinity.data.db.dao.UserDao
import com.example.greenfinity.data.db.entity.Quest
import com.example.greenfinity.data.db.entity.QuestCompletion
import com.example.greenfinity.data.db.entity.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [User::class, Quest::class, QuestCompletion::class], version = 5, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun questDao(): QuestDao
    abstract fun questCompletionDao(): QuestCompletionDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "greenfinity_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(DatabaseCallback(context))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }


    private class DatabaseCallback(private val context: Context) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    val questDao = database.questDao()

                    val initialQuests = listOf(
                        Quest(title = "Menanam Pohon", description = "Tanam pohon di halaman rumah", points = 12, imageResName = "quest_tree"),
                        Quest(title = "Membuang Sampah", description = "Luangkan waktu 15 menit untuk membuang sampah", points = 8, imageResName = "quest_bin"),
                        Quest(title = "Daur Ulang Botol", description = "Kumpulkan minimal 10 botol untuk di daur ulang", points = 10, imageResName = "quest_bottles")
                    )
                    questDao.insertAll(initialQuests)
                }
            }
        }
    }
}