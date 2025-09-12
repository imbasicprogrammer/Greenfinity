package com.example.greenfinity.features.auth.reset_password

import androidx.lifecycle.ViewModel
import com.example.greenfinity.data.db.dao.UserDao

class ResetPasswordViewModel(private val userDao: UserDao) : ViewModel() {

    suspend fun updatePassword(identifier: String, newPassword: String): Boolean {
        // Cari user berdasarkan username atau email
        val user = userDao.findUserByIdentifier(identifier.trim())

        return if (user != null) {
            // Jika user ditemukan, update passwordnya
            val updatedUser = user.copy(password = newPassword)
            userDao.updateUser(updatedUser)
            true // Kembalikan true jika berhasil
        } else {
            false // Kembalikan false jika user tidak ditemukan
        }
    }
}