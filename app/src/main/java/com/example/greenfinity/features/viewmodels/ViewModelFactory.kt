package com.example.greenfinity.features.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.greenfinity.data.db.AppDatabase
import com.example.greenfinity.features.auth.reset_password.ResetPasswordViewModel
import com.example.greenfinity.features.challenges.ChallengesViewModel
import com.example.greenfinity.features.home.HomeViewModel
import com.example.greenfinity.features.profile.ProfileViewModel
import com.example.greenfinity.features.quest_detail.QuestViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val database = AppDatabase.getDatabase(context)
        if (modelClass.isAssignableFrom(ChallengesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChallengesViewModel(AppDatabase.getDatabase(context).questDao()) as T
        }

        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(database.userDao(), database.questDao(), database.questCompletionDao()) as T
        }
        if (modelClass.isAssignableFrom(QuestViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuestViewModel(database.userDao(), database.questDao(), database.questCompletionDao()) as T
        }

        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(database.userDao(), database.questCompletionDao()) as T
        }

        if (modelClass.isAssignableFrom(ResetPasswordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ResetPasswordViewModel(database.userDao()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }


}