package com.example.greenfinity.features.auth.reset_password

import androidx.lifecycle.ViewModel
import com.example.greenfinity.data.db.dao.UserDao

class ResetPasswordViewModel(private val userDao: UserDao) : ViewModel() {

    suspend fun updatePassword(identifier: String, newPassword: String): Boolean {
        val user = userDao.findUserByIdentifier(identifier.trim())

        return if (user != null) {

            val updatedUser = user.copy(password = newPassword)
            userDao.updateUser(updatedUser)
            true
        } else {
            false
        }
    }
}