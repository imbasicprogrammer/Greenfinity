package com.example.greenfinity.features.auth.introduction

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.greenfinity.R
import com.example.greenfinity.data.model.Avatar
import com.example.greenfinity.features.auth.components.AuthBottomBar
import com.example.greenfinity.ui.theme.*

val moods = listOf("Anxiety", "Distracted", "Joy", "Surprised", "Stupid", "Calm")

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun IntroductionScreen(
    username: String,
    avatar: Avatar?,
    onNavigateBack: () -> Unit,
    onFinish: () -> Unit
) {
    var selectedMood by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    // Konversi nama file (String) dari avatar yang dipilih menjadi ID Resource (Int)
    val imageId = remember(avatar?.imageResName) {
        if (avatar != null) {
            context.resources.getIdentifier(
                avatar.imageResName,
                "drawable",
                context.packageName
            )
        } else {
            0 // Default jika tidak ada avatar
        }
    }

    Scaffold(
        containerColor = LightBackgroundGreen,
        bottomBar = {
            AuthBottomBar(
                currentPage = 2,
                onBack = onNavigateBack,
                onNext = onFinish,
                nextButtonText = "Finish"
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                // Gunakan imageId yang sudah dikonversi
                painter = painterResource(id = if (imageId != 0) imageId else R.drawable.cat_thumb),
                contentDescription = avatar?.name ?: "Avatar",
                modifier = Modifier.height(200.dp)
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp, bottomEnd = 12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Text(
                        text = username.ifEmpty { "Greeny" },
                        color = DarkGreen,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                    )
                }
                Surface(
                    color = DarkGreen,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.offset(y = (-8).dp)
                ) {
                    Text(
                        // Gunakan nama dari objek avatar
                        text = "Hi ${username.ifEmpty { avatar?.name ?: "Player" }}, what's\nyour mood like today?",
                        color = TextWhite,
                        modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                maxItemsInEachRow = 3
            ) {
                moods.forEach { mood ->
                    val isSelected = mood == selectedMood
                    MoodButton(
                        text = mood,
                        isSelected = isSelected,
                        onClick = { selectedMood = mood }
                    )
                }
            }
        }
    }
}

@Composable
private fun MoodButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val containerColor = if (isSelected) DarkGreen else ButtonGreen
    val contentColor = if (isSelected) TextWhite else TextWhite.copy(alpha = 0.9f)

    Button(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        modifier = Modifier.padding(horizontal = 6.dp)
    ) {
        Text(text = text)
    }
}

// Perbarui dummy data agar cocok dengan data class (menggunakan String)
private val dummyAvatarForPreview = Avatar("Berrychan", "cat_strawberry")

@Preview(showBackground = true, device = "id:pixel_4")
@Composable
fun IntroductionScreenPreview() {
    IntroductionScreen(
        username = "Greeny",
        avatar = dummyAvatarForPreview,
        onNavigateBack = {},
        onFinish = {}
    )
}