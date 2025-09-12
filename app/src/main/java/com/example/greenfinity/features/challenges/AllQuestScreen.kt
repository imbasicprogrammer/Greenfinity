package com.example.greenfinity.features.challenges

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
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
fun AllQuestScreen(
    viewModel: ChallengesViewModel = viewModel(factory = ViewModelFactory(LocalContext.current)),
    onQuestClicked: (Int) -> Unit
) {
    val quests by viewModel.quests.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBackgroundGreen),
        contentPadding = PaddingValues(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            HintCard()
            Spacer(modifier = Modifier.height(8.dp))
            Divider(color = MediumGreen.copy(alpha = 0.2f))
        }

        items(quests) { quest ->
            QuestItemCard(quest = quest, onClick = { onQuestClicked(quest.id) })
        }
    }
}

@Composable
private fun HintCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(DarkGreen)
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        Text(
            text = "klik untuk melihat detail quest",
            color = TextWhite,
            fontSize = 18.sp,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .fillMaxWidth(0.6f)
        )
        Image(
            painter = painterResource(id = R.drawable.cat_headphone),
            contentDescription = "Mascot",
            modifier = Modifier
                .size(90.dp)
                .align(Alignment.CenterEnd)
        )
    }
}

@Composable
private fun QuestItemCard(quest: Quest, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.CenterStart
    ) {
        // Card Teks (di belakang)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                // 1. UBAH TINGGI CARD TEKS MENJADI LEBIH PENDEK
                .height(110.dp)
                .padding(start = 65.dp)
                // 2. POSISIKAN DI TENGAH SECARA VERTIKAL
                .align(Alignment.Center),
            shape = RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp, topEnd = 55.dp, bottomEnd = 55.dp),
            colors = CardDefaults.cardColors(containerColor = MediumGreen),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 81.dp, end = 40.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = quest.title,
                    color = TextWhite,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 24.sp
                )
            }
        }

        // Card Gambar (di depan)
        Card(
            modifier = Modifier
                .size(130.dp), // Ukuran tetap 130dp
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            val context = LocalContext.current
            val imageId = remember(quest.imageResName) {
                context.resources.getIdentifier(quest.imageResName, "drawable", context.packageName)
            }
            Image(
                painter = painterResource(id = if (imageId != 0) imageId else R.drawable.cat_thumb),
                contentDescription = quest.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AllQuestScreenPreview() {
    AllQuestScreen(onQuestClicked = {})
}