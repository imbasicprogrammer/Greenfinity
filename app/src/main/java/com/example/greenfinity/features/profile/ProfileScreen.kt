package com.example.greenfinity.features.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.greenfinity.R
import com.example.greenfinity.data.db.entity.QuestCompletion
import com.example.greenfinity.features.viewmodels.ViewModelFactory
import com.example.greenfinity.ui.theme.*

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel(factory = ViewModelFactory(LocalContext.current)),
    onLogoutClicked: () -> Unit
) {
    val user by viewModel.currentUser.collectAsState()
    val history by viewModel.completedQuests.collectAsState()

    val pointsForNextLevel = (user?.level ?: 1) * 100
    val progress = if (pointsForNextLevel > 0) {
        (user?.points?.toFloat() ?: 0f) / pointsForNextLevel
    } else {
        0f
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBackgroundGreen)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Bagian Header
        item {
            Spacer(modifier = Modifier.height(24.dp))
            ProfileHeader(onLogoutClicked = onLogoutClicked)
            Spacer(modifier = Modifier.height(24.dp))
        }

        // Bagian Info User
        item {
            UserInfoSection(
                avatarRes = R.drawable.cat_thumb,
                username = user?.username ?: "Memuat...",
                level = user?.level ?: 1,
                progress = progress
            )
            Spacer(modifier = Modifier.height(24.dp))
        }

        // Bagian Statistik
        item {
            StatsSection(
                completedQuestsCount = history.size,
                totalPoints = user?.points ?: 0
            )
            Spacer(modifier = Modifier.height(24.dp))
            Divider(color = MediumGreen.copy(alpha = 0.2f))
            Spacer(modifier = Modifier.height(16.dp))
        }


        item {
            Text(
                text = "History",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = DarkGreen,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
        }

        if (history.isEmpty()) {
            item {
                Text(
                    "Belum ada quest yang diselesaikan.",
                    color = DarkGreen.copy(alpha = 0.7f),
                    modifier = Modifier.padding(vertical = 24.dp)
                )
            }
        } else {
            items(history) { questCompletion ->
                CompletedQuestCard(quest = questCompletion)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun ProfileHeader(onLogoutClicked: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Profile", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = DarkGreen)
        Button(
            onClick = onLogoutClicked,
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = MediumGreen,
                contentColor = TextWhite
            )
        ) {
            Icon(Icons.Default.Logout, contentDescription = "Logout")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Logout")
        }
    }
}

@Composable
private fun UserInfoSection(avatarRes: Int, username: String, level: Int, progress: Float) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = avatarRes),
            contentDescription = "Avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(username, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = DarkGreen)
            Text("Level $level", fontSize = 16.sp, color = DarkGreen.copy(alpha = 0.7f))
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(CircleShape),
                color = MediumGreen,
                trackColor = ButtonGreen.copy(alpha = 0.3f)
            )
        }
    }
}

@Composable
private fun StatsSection(completedQuestsCount: Int, totalPoints: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        StatCard(
            modifier = Modifier.weight(1f),
            icon = Icons.Default.TaskAlt,
            value = completedQuestsCount.toString(),
            label = "Quest Selesai"
        )
        StatCard(
            modifier = Modifier.weight(1f),
            icon = Icons.Default.EmojiEvents,
            value = totalPoints.toString(),
            label = "Total Poin"
        )
    }
}

@Composable
private fun StatCard(modifier: Modifier = Modifier, icon: ImageVector, value: String, label: String) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = ButtonGreen.copy(alpha = 0.4f)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = DarkGreen,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(value, fontWeight = FontWeight.Bold, color = DarkGreen, fontSize = 18.sp)
                Text(label, color = DarkGreen.copy(alpha = 0.8f), fontSize = 12.sp)
            }
        }
    }
}

@Composable
private fun CompletedQuestCard(quest: QuestCompletion) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkGreen),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = quest.questTitle,
                color = TextWhite,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "${quest.pointsAwarded} P",
                color = MediumGreen,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(onLogoutClicked = {})
}