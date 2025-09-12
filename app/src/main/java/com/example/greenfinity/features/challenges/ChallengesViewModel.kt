package com.example.greenfinity.features.challenges

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greenfinity.data.db.dao.QuestDao
import com.example.greenfinity.data.db.entity.Quest
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class ChallengesViewModel(private val questDao: QuestDao) : ViewModel() {
    // Ambil daftar quest dari database dan jadikan StateFlow
    // UI akan otomatis update jika data quest di database berubah
    val quests: StateFlow<List<Quest>> = questDao.getAllQuests()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )
}