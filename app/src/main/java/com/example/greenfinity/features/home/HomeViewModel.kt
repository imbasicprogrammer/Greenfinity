package com.example.greenfinity.features.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greenfinity.data.db.dao.QuestCompletionDao
import com.example.greenfinity.data.db.dao.QuestDao
import com.example.greenfinity.data.db.dao.UserDao
import com.example.greenfinity.data.db.entity.Quest
import com.example.greenfinity.data.db.entity.QuestCompletion
import com.example.greenfinity.data.db.entity.User
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    userDao: UserDao,
    questDao: QuestDao,
    questCompletionDao: QuestCompletionDao
) : ViewModel() {

    // State untuk data user yang sedang login
    val currentUser: StateFlow<User?> = userDao.getCurrentUser()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // State untuk progres level (0.0f - 1.0f)
    val levelProgress: StateFlow<Float> = currentUser.map { user ->
        if (user == null) return@map 0f
        val pointsForNextLevel = user.level * 100 // Contoh: Level 1 butuh 100, Level 2 butuh 200
        user.points.toFloat() / pointsForNextLevel
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0f)

    // State untuk quest utama hari ini (ambil satu secara acak)
    val mainQuest: StateFlow<Quest?> = questDao.getAllQuests().map { it.randomOrNull() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // State untuk ringkasan progres
    val recentCompletions: StateFlow<List<QuestCompletion>> = questCompletionDao.getRecentCompletions()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}