// navigation/AppNavigation.kt (KODE LENGKAP PENGGANTI FINAL)

package com.example.greenfinity.navigation

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.greenfinity.data.db.AppDatabase
import com.example.greenfinity.data.db.entity.User
import com.example.greenfinity.data.model.Avatar
import com.example.greenfinity.data.preferences.LoginSession
import com.example.greenfinity.data.preferences.UserPreferences
import com.example.greenfinity.features.MainScreen
import com.example.greenfinity.features.auth.avatar_choice.AvatarChooseScreen
import com.example.greenfinity.features.auth.choice.AuthChoiceScreen
import com.example.greenfinity.features.auth.introduction.IntroductionScreen
import com.example.greenfinity.features.auth.login.LoginScreen
import com.example.greenfinity.features.auth.register.RegisterScreen
import com.example.greenfinity.features.auth.reset_password.CreateNewPasswordScreen
import com.example.greenfinity.features.auth.user_form.UserFormScreen
import com.example.greenfinity.features.auth.welcome.WelcomeScreen
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


object AppRoutes {
    const val WELCOME = "welcome"
    const val AUTH_CHOICE = "auth_choice"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val USER_FORM = "user_form"
    const val AVATAR_CHOICE = "avatar_choice"
    const val INTRODUCTION = "introduction"
    const val HOME = "home"
    const val RESET_PASSWORD = "reset_password"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Inisialisasi kedua sumber data
    val userDao = remember { AppDatabase.getDatabase(context).userDao() }
    val userPreferences = remember { UserPreferences(context) }

    // State untuk sesi login (dari DataStore) dan status loading
    var session by remember { mutableStateOf<LoginSession?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = Unit) {
        session = userPreferences.getLoginSession().first()
        isLoading = false
    }

    // State untuk alur registrasi
    var registrationData by remember { mutableStateOf(User(id = 0, username = "", email = "", password = "")) }
    var selectedAvatarState by remember { mutableStateOf<Avatar?>(null) }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
    } else {
        // Halaman awal ditentukan oleh sesi di DataStore
        val startDestination = if (session?.isLoggedIn == true) AppRoutes.HOME else AppRoutes.WELCOME

        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            composable(AppRoutes.WELCOME) { WelcomeScreen(onGetStarted = { navController.navigate(AppRoutes.AUTH_CHOICE) }) }

            composable(AppRoutes.AUTH_CHOICE) { AuthChoiceScreen(onRegisterClicked = { navController.navigate(AppRoutes.REGISTER) }, onLoginClicked = { navController.navigate(AppRoutes.LOGIN) }) }

            composable(AppRoutes.REGISTER) {
                RegisterScreen(
                    onRegisterClicked = { name, email, password ->
                        // Simpan semua data registrasi ke dalam state
                        registrationData = User(username = name, email = email, password = password)
                        navController.navigate(AppRoutes.USER_FORM)
                    },
                    onNavigateToLogin = { navController.navigate(AppRoutes.LOGIN) { popUpTo(AppRoutes.AUTH_CHOICE) } }
                )
            }

            composable(AppRoutes.LOGIN) {
                LoginScreen(
                    onLoginClicked = { email, password ->
                        scope.launch {
                            val user = userDao.getUserByCredentials(email, password)
                            if (user != null) {
                                userPreferences.saveLoginSession()
                                navController.navigate(AppRoutes.HOME) {
                                    popUpTo(AppRoutes.WELCOME) { inclusive = true }
                                }
                            } else {
                                Toast.makeText(context, "Email atau Password Salah!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    onNavigateToRegister = { navController.navigate(AppRoutes.REGISTER) { popUpTo(AppRoutes.AUTH_CHOICE) } },
                    onForgotPasswordClicked = { navController.navigate(AppRoutes.RESET_PASSWORD) }
                )
            }

            composable(AppRoutes.RESET_PASSWORD) {
                CreateNewPasswordScreen(
                    onPasswordSaved = {
                        // Kembali ke halaman login setelah password berhasil disimpan
                        navController.popBackStack()
                    }
                )
            }

            composable(AppRoutes.USER_FORM) {
                UserFormScreen(
                    // Kirim username yang sudah tersimpan dari RegisterScreen
                    initialUsername = registrationData.username,
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateNext = { usernameDariForm ->
                        // Update username jika diubah di form
                        registrationData = registrationData.copy(username = usernameDariForm)
                        navController.navigate(AppRoutes.AVATAR_CHOICE)
                    }
                )
            }

            composable(AppRoutes.AVATAR_CHOICE) {
                AvatarChooseScreen(
                    username = registrationData.username,
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateNext = { avatarYangDipilih ->
                        // Simpan avatar yang dipilih ke state utama
                        selectedAvatarState = avatarYangDipilih
                        // Pindah ke layar selanjutnya
                        navController.navigate(AppRoutes.INTRODUCTION)
                    }
                )
            }

            composable(AppRoutes.INTRODUCTION) {
                IntroductionScreen(
                    username = registrationData.username,
                    avatar = selectedAvatarState,
                    onNavigateBack = { navController.popBackStack() },
                    onFinish = {
                        scope.launch {
                            // 1. Simpan akun permanen di Room
                            userDao.insertUser(registrationData)
                            // 2. Simpan sesi login di DataStore
                            userPreferences.saveLoginSession()
                            // 3. Navigasi ke Home
                            navController.navigate(AppRoutes.HOME) {
                                popUpTo(AppRoutes.WELCOME) { inclusive = true }
                            }
                        }
                    }
                )
            }

            composable(AppRoutes.HOME) {
                MainScreen(onLogout = {
                    scope.launch {
                        userPreferences.clearLoginSession()
                        navController.navigate(AppRoutes.AUTH_CHOICE) {
                            popUpTo(AppRoutes.HOME) { inclusive = true }
                        }
                    }
                })
            }
        }
    }
}