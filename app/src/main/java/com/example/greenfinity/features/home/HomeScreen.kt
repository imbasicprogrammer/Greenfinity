package com.example.greenfinity.features.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.greenfinity.R
import com.example.greenfinity.data.db.entity.Quest
import com.example.greenfinity.features.viewmodels.ViewModelFactory
import com.example.greenfinity.ui.theme.*

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(factory = ViewModelFactory(LocalContext.current)),
    onNavigateToQuestDetail: (Int) -> Unit
) {
    // Ambil semua data dari ViewModel
    val user by viewModel.currentUser.collectAsState()
    val progress by viewModel.levelProgress.collectAsState()
    val mainQuest by viewModel.mainQuest.collectAsState()
    val recentTasks by viewModel.recentCompletions.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBackgroundGreen)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Gunakan data user untuk sapaan
        HeaderSection(username = user?.username ?: "pahlawan")
        MotivationCard()
        // Gunakan data progress dan tasks
        ProgressSection(progress = progress, tasks = recentTasks.map { it.questTitle })
        // Gunakan data main quest
        mainQuest?.let {
            MainQuestCard(quest = it, onNavigateToQuestDetail = onNavigateToQuestDetail)
        }
    }
}

@Composable
private fun HeaderSection(username: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Selamat datang, $username!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = DarkGreen
        )
        Text(
            text = "Perubahan dimulai darimu",
            fontSize = 16.sp,
            color = DarkGreen.copy(alpha = 0.7f)
        )
    }
}

@Composable
private fun MotivationCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(DarkGreen)
            .padding(20.dp)
    ) {
        Text(
            text = "Aku percaya kamu bisa, ayo mulai!",
            color = TextWhite,
            fontSize = 16.sp,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .fillMaxWidth(0.6f)
        )
        Image(
            painter = painterResource(id = R.drawable.cat_thumb),
            contentDescription = "Motivation Cat",
            modifier = Modifier
                .size(90.dp)
                .align(Alignment.CenterEnd)
        )
    }
}

@Composable
private fun ProgressSection(progress: Float, tasks: List<String>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CircularProgress(progress = progress, modifier = Modifier.size(120.dp))
        Card(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = DarkGreen),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Ringkasan Progres", color = TextWhite, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                if (tasks.isEmpty()) {
                    Text("Belum ada quest selesai", color = TextWhite.copy(alpha = 0.7f))
                } else {
                    tasks.forEach { taskName ->
                        ProgressTaskItem(taskName)
                    }
                }
            }
        }
    }
}

@Composable
private fun CircularProgress(progress: Float, modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawArc(
                color = MediumGreen.copy(alpha = 0.3f),
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 30f, cap = StrokeCap.Round)
            )
            drawArc(
                color = MediumGreen,
                startAngle = -90f,
                sweepAngle = 360 * progress,
                useCenter = false,
                style = Stroke(width = 30f, cap = StrokeCap.Round)
            )
        }
    }
}

@Composable
private fun ProgressTaskItem(taskName: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = true,
            onCheckedChange = {},
            enabled = false,
            colors = CheckboxDefaults.colors(
                checkedColor = MediumGreen,
                disabledCheckedColor = MediumGreen
            )
        )
        Text(text = taskName, color = TextWhite)
    }
}

@Composable
private fun MainQuestCard(
    quest: Quest,
    onNavigateToQuestDetail: (Int) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = DarkGreen),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text("MAIN QUEST", color = TextWhite.copy(alpha = 0.8f), fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    quest.title,
                    color = TextWhite,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Surface(
                    color = MediumGreen,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        "${quest.points} P",
                        color = TextWhite,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { onNavigateToQuestDetail(quest.id) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE0E0E0),
                        contentColor = DarkGreen
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Detail", fontWeight = FontWeight.Bold)
                }
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play Quest",
                    tint = TextWhite,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(onNavigateToQuestDetail = {})
}