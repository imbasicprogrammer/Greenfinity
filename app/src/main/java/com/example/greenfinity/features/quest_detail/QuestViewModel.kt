package com.example.greenfinity.features.quest_detail


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greenfinity.data.db.dao.QuestCompletionDao
import com.example.greenfinity.data.db.dao.QuestDao
import com.example.greenfinity.data.db.dao.UserDao
import com.example.greenfinity.data.db.entity.Quest
import com.example.greenfinity.data.db.entity.QuestCompletion
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class QuestViewModel(
    private val userDao: UserDao,
    private val questDao: QuestDao,
    private val questCompletionDao: QuestCompletionDao
) : ViewModel() {

    private val _selectedQuest = MutableStateFlow<Quest?>(null)
    val selectedQuest: StateFlow<Quest?> = _selectedQuest.asStateFlow()

    private val _timeLeftInMillis = MutableStateFlow(0L)
    val timeLeftFormatted: StateFlow<String> = _timeLeftInMillis.map { formatTime(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "00:00")

    fun selectQuest(questId: Int) {
        viewModelScope.launch {
            _selectedQuest.value = questDao.getQuestById(questId)
        }
    }

    fun startQuestTimer(durationMinutes: Int = 15) {
        val totalTime = durationMinutes * 60 * 1000L
        _timeLeftInMillis.value = totalTime
        viewModelScope.launch {
            while (_timeLeftInMillis.value > 0) {
                delay(1000L)
                _timeLeftInMillis.value -= 1000L
            }
        }
    }

    fun finishQuest(onQuestFinished: () -> Unit) {
        viewModelScope.launch {
            val quest = _selectedQuest.value
            val user = userDao.getCurrentUser().firstOrNull()

            if (quest != null && user != null) {
                var newPoints = user.points + quest.points
                var newLevel = user.level

                val pointsNeededForNextLevel = newLevel * 100
                if (newPoints >= pointsNeededForNextLevel) {
                    newLevel++
                    newPoints -= pointsNeededForNextLevel

                }

                val updatedUser = user.copy(points = newPoints, level = newLevel)
                userDao.updateUser(updatedUser)

                val completion = QuestCompletion(
                    questId = quest.id,
                    questTitle = quest.title,
                    pointsAwarded = quest.points
                )
                questCompletionDao.insert(completion)

                onQuestFinished()
            }
        }
    }

    private fun formatTime(millis: Long): String {
        val totalSeconds = millis / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format("%02d:%02d", minutes, seconds)
    }
}