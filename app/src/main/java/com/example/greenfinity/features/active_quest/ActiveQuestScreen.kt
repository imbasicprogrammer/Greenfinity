package com.example.greenfinity.features.active_quest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.greenfinity.features.quest_detail.QuestViewModel
import com.example.greenfinity.features.viewmodels.ViewModelFactory
import com.example.greenfinity.ui.theme.*

@Composable
fun ActiveQuestScreen(

    viewModel: QuestViewModel,
    onQuestFinished: () -> Unit
) {

    val quest by viewModel.selectedQuest.collectAsState()
    val timeLeft by viewModel.timeLeftFormatted.collectAsState()



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBackgroundGreen)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Bagian Atas: Judul
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Quest Berlangsung",
                fontSize = 18.sp,
                color = DarkGreen.copy(alpha = 0.7f)
            )
            Text(
                text = quest?.title ?: "Memuat Quest...",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = DarkGreen,
                textAlign = TextAlign.Center
            )
        }

        // Bagian Tengah: Timer
        Box(
            modifier = Modifier.size(250.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                // Anda bisa membuat progress indicator dinamis dari ViewModel jika mau
                progress = { 1f }, // Sementara dibuat penuh
                modifier = Modifier.fillMaxSize(),
                color = MediumGreen,
                trackColor = ButtonGreen.copy(alpha = 0.3f),
                strokeWidth = 20.dp,
                strokeCap = StrokeCap.Round
            )
            Text(
                text = timeLeft, // Tampilkan waktu dari ViewModel
                fontSize = 52.sp,
                fontWeight = FontWeight.Bold,
                color = DarkGreen
            )
        }

        // Bagian Bawah: Tombol Selesai
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Waktu berjalan, selesaikan misimu!",
                fontSize = 16.sp,
                color = DarkGreen.copy(alpha = 0.8f),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Button(
                onClick = {
                    // Panggil fungsi finishQuest di ViewModel
                    viewModel.finishQuest(onQuestFinished)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .shadow(elevation = 8.dp, shape = CircleShape, spotColor = DarkGreen),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = ButtonGreen)
            ) {
                Text("Selesai Quest", color = TextWhite, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ActiveQuestScreenPreview() {

    GreenfinityTheme {
        ActiveQuestScreen(
            viewModel = viewModel(factory = ViewModelFactory(LocalContext.current)),
            onQuestFinished = {}
        )
    }
}