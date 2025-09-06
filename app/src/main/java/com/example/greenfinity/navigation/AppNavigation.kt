package com.example.greenfinity.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.greenfinity.data.model.Avatar
import com.example.greenfinity.features.auth.choice.AuthChoiceScreen
import com.example.greenfinity.features.auth.welcome.WelcomeScreen
import com.example.greenfinity.features.auth.login.LoginScreen
import com.example.greenfinity.features.auth.register.RegisterScreen
import com.example.greenfinity.features.auth.user_form.UserFormScreen
import com.example.greenfinity.features.auth.avatar_choice.AvatarChooseScreen
import com.example.greenfinity.features.auth.introduction.IntroductionScreen

// Definisikan nama rute agar tidak ada typo
object AppRoutes {
    const val WELCOME = "welcome"
    const val AUTH_CHOICE = "auth_choice"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val USER_FORM = "user_form"
    const val AVATAR_CHOICE = "avatar_choice"
    const val INTRODUCTION = "introduction"
    const val HOME = "home"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    var usernameState by remember { mutableStateOf("") }
    var selectedAvatarState by remember { mutableStateOf<Avatar?>(null) } // <-- 2. VARIABEL AVATAR DITAMBAHKAN

    NavHost(
        navController = navController,
        startDestination = AppRoutes.WELCOME
    ) {
        composable(AppRoutes.WELCOME) {
            WelcomeScreen(
                onGetStartedClicked = {
                    navController.navigate(AppRoutes.AUTH_CHOICE)
                }
            )
        }

        composable(AppRoutes.AUTH_CHOICE) {
            AuthChoiceScreen(
                onRegisterClicked = {
                    navController.navigate(AppRoutes.REGISTER)
                },
                onLoginClicked = {
                    navController.navigate(AppRoutes.LOGIN)
                }
            )
        }

        composable(AppRoutes.REGISTER) {
            RegisterScreen(
                onRegisterClicked = { name, email, password ->
                    // TODO: Logika untuk menyimpan data register

                    // Setelah register berhasil, navigasi ke form user
                    navController.navigate(AppRoutes.USER_FORM) {
                        // Hapus semua halaman auth dari backstack
                        popUpTo(AppRoutes.WELCOME) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToLogin = {
                    navController.navigate(AppRoutes.LOGIN) {
                        popUpTo(AppRoutes.AUTH_CHOICE)
                    }
                }
            )
        }


        composable(AppRoutes.USER_FORM) {
            UserFormScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateNext = { usernameDariForm ->
                    usernameState = usernameDariForm
                    navController.navigate(AppRoutes.AVATAR_CHOICE)
                }
            )
        }

        composable(AppRoutes.AVATAR_CHOICE) {
            AvatarChooseScreen(
                username = usernameState,
                onNavigateBack = { navController.popBackStack() },
                onNavigateNext = { avatarYangDipilih ->
                    selectedAvatarState = avatarYangDipilih
                    navController.navigate(AppRoutes.INTRODUCTION)
                }
            )
        }

        composable(AppRoutes.INTRODUCTION) {
            IntroductionScreen(
                username = usernameState,
                avatar = selectedAvatarState, // <-- Kirim avatar
                onNavigateBack = { navController.popBackStack() },
                onFinish = {
                    navController.navigate(AppRoutes.HOME) { // <-- Panggil HOME
                        popUpTo(AppRoutes.WELCOME) { inclusive = true }
                    }
                }
            )
        }


        composable(AppRoutes.LOGIN) {
            LoginScreen(
                onLoginClicked = { email, password ->
                    // TODO: Logika untuk login
                },
                onNavigateToRegister = {
                    navController.navigate(AppRoutes.REGISTER) {
                        // Hapus halaman login dari backstack agar tidak kembali ke sana
                        popUpTo(AppRoutes.AUTH_CHOICE)
                    }
                }
            )
        }

        composable(AppRoutes.HOME) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Selamat Datang di Beranda!", fontSize = 24.sp)
            }
        }
    }
}