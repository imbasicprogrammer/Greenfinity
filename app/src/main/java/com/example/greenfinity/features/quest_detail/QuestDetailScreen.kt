package com.example.greenfinity.features.quest_detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
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


data class QuestDetail(
    val id: Int,
    val title: String,
    val description: String,
    val points: Int,
    @DrawableRes val imageRes: Int
)

val allQuestsDetails = listOf(
    QuestDetail(1, "Menanam Pohon", "Tanam pohon...", 12, R.drawable.quest_tree)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestDetailScreen(
    viewModel: QuestViewModel,
    onBack: () -> Unit,
    onStartQuest: (Int) -> Unit
) {
    val quest by viewModel.selectedQuest.collectAsState()
    val context = LocalContext.current

    if (quest == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val imageId = remember(quest!!.imageResName) {
        context.resources.getIdentifier(
            quest!!.imageResName,
            "drawable",
            context.packageName
        )
    }

    Scaffold(
        containerColor = LightBackgroundGreen,
        topBar = {
            TopAppBar(
                title = { Text("Detail Quest", color = DarkGreen, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = DarkGreen)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = if (imageId != 0) imageId else R.drawable.cat_thumb),
                contentDescription = quest!!.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(20.dp))
            )
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = quest!!.title,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkGreen,
                    modifier = Modifier.weight(1f)
                )
                Surface(
                    color = MediumGreen,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        "${quest!!.points} P",
                        color = TextWhite,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = MediumGreen.copy(alpha = 0.2f))
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Tentang Quest Ini",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = DarkGreen,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = quest!!.description,
                fontSize = 16.sp,
                color = DarkGreen.copy(alpha = 0.8f),
                lineHeight = 24.sp,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    viewModel.startQuestTimer()
                    onStartQuest(quest!!.id)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(vertical = 8.dp)
                    .shadow(elevation = 8.dp, shape = CircleShape, spotColor = DarkGreen),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = ButtonGreen)
            ) {
                Text("Mulai Quest", color = TextWhite, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuestDetailScreenPreview() {
    QuestDetailScreen(
        viewModel = viewModel(factory = ViewModelFactory(LocalContext.current)),
        onBack = {},
        onStartQuest = {}
    )
}