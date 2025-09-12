package com.example.greenfinity.features.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greenfinity.data.db.dao.QuestCompletionDao
import com.example.greenfinity.data.db.dao.UserDao
import com.example.greenfinity.data.db.entity.QuestCompletion
import com.example.greenfinity.data.db.entity.User
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class ProfileViewModel(
    userDao: UserDao,
    questCompletionDao: QuestCompletionDao
) : ViewModel() {

    val currentUser: StateFlow<User?> = userDao.getCurrentUser()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val completedQuests: StateFlow<List<QuestCompletion>> = questCompletionDao.getAllCompletions()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}