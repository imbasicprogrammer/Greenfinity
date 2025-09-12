package com.example.greenfinity.features

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.greenfinity.R
import com.example.greenfinity.features.active_quest.ActiveQuestScreen
import com.example.greenfinity.features.challenges.AllQuestScreen
import com.example.greenfinity.features.home.HomeScreen
import com.example.greenfinity.features.profile.ProfileScreen
import com.example.greenfinity.features.quest_detail.QuestDetailScreen
import com.example.greenfinity.features.quest_detail.QuestViewModel
import com.example.greenfinity.features.viewmodels.ViewModelFactory
import com.example.greenfinity.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onLogout: () -> Unit) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Buat Factory dan ViewModel sekali saja di sini agar bisa dibagikan
    val factory = ViewModelFactory(LocalContext.current)
    val questViewModel: QuestViewModel = viewModel(factory = factory)

    Scaffold(
        bottomBar = { AppBottomBarManual(navController = navController, currentRoute = currentRoute) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                HomeScreen(
                    viewModel = viewModel(factory = factory),
                    onNavigateToQuestDetail = { questId ->
                        // Logika yang sama seperti di AllQuestScreen
                        questViewModel.selectQuest(questId)
                        navController.navigate("quest_detail/$questId")
                    }
                )
            }


            composable("challenges") {
                AllQuestScreen(
                    viewModel = viewModel(factory = factory),
                    onQuestClicked = { questId ->
                        // Saat quest diklik, pilih quest di ViewModel dan navigasi
                        questViewModel.selectQuest(questId)
                        navController.navigate("quest_detail/$questId")
                    }
                )
            }

            composable("tips") {
                // Placeholder untuk Tips Screen
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Tips Screen", fontSize = 24.sp)
                }
            }

            composable("profile") {
                ProfileScreen(onLogoutClicked = onLogout)
            }

            composable(
                route = "quest_detail/{questId}",
                arguments = listOf(navArgument("questId") { type = NavType.IntType })
            ) {
                // Panggil QuestDetailScreen dengan mengirim ViewModel
                QuestDetailScreen(
                    viewModel = questViewModel,
                    onBack = { navController.popBackStack() },
                    onStartQuest = { questId ->
                        navController.navigate("active_quest/$questId")
                    }
                )
            }

            composable(
                route = "active_quest/{questId}",
                arguments = listOf(navArgument("questId") { type = NavType.IntType })
            ) {
                ActiveQuestScreen(
                    viewModel = questViewModel,
                    onQuestFinished = {
                        // Kembali ke daftar quest setelah selesai
                        navController.popBackStack("challenges", inclusive = false)
                    }
                )
            }
        }
    }
}

@Composable
private fun AppBottomBarManual(navController: NavController, currentRoute: String?) {
    Box {
        NavigationBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .height(80.dp),
            containerColor = DarkGreen,
            contentColor = TextWhite.copy(alpha = 0.7f)
        ) {
            NavigationBarItem(
                selected = currentRoute == "challenges",
                onClick = { navController.navigate("challenges") { launchSingleTop = true } },
                icon = { Icon(painterResource(id = R.drawable.ic_rocket), contentDescription = "Challenges") },
                colors = NavigationBarItemDefaults.colors(indicatorColor = MediumGreen)
            )
            NavigationBarItem(
                selected = currentRoute == "tips",
                onClick = { navController.navigate("tips") { launchSingleTop = true } },
                icon = { Icon(painterResource(id = R.drawable.ic_lightbulb), contentDescription = "Tips") },
                colors = NavigationBarItemDefaults.colors(indicatorColor = MediumGreen)
            )
            NavigationBarItem(
                selected = currentRoute == "profile",
                onClick = { navController.navigate("profile") { launchSingleTop = true } },
                icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                colors = NavigationBarItemDefaults.colors(indicatorColor = MediumGreen)
            )
        }

        FloatingActionButton(
            onClick = { navController.navigate("home") { launchSingleTop = true } },
            shape = CircleShape,
            containerColor = if (currentRoute == "home") TextWhite else MediumGreen,
            contentColor = if (currentRoute == "home") MediumGreen else TextWhite,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-24).dp)
        ) {
            Icon(Icons.Default.Home, contentDescription = "Home", modifier = Modifier.size(32.dp))
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(onLogout = {})
}