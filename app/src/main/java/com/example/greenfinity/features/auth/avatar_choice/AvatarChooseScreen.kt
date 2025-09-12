package com.example.greenfinity.features.auth.avatar_choice


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.greenfinity.R
import com.example.greenfinity.data.model.Avatar
import com.example.greenfinity.features.auth.components.AuthBottomBar
import com.example.greenfinity.ui.theme.*

val dummyAvatars = listOf(
    Avatar("Berrychan", "cat_strawberry"),
    Avatar("Bready", "cat_toast"),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AvatarChooseScreen(
    username: String,
    onNavigateBack: () -> Unit,
    onNavigateNext: (Avatar) -> Unit
) {
    var selectedAvatar by remember { mutableStateOf<Avatar?>(null) }

    Scaffold(
        containerColor = LightBackgroundGreen,
        bottomBar = {
            AuthBottomBar(
                currentPage = 1,
                onBack = onNavigateBack,
                onNext = { selectedAvatar?.let { onNavigateNext(it) } }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(64.dp))
            HeaderWithAvatar(username = username)
            Spacer(modifier = Modifier.height(32.dp))

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(dummyAvatars) { avatar ->
                    AvatarItem(
                        avatar = avatar,
                        isSelected = avatar == selectedAvatar,
                        onClick = { selectedAvatar = avatar }
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { selectedAvatar?.let { onNavigateNext(it) } },
                enabled = selectedAvatar != null,
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(60.dp)
                    .shadow(elevation = 8.dp, shape = CircleShape, spotColor = DarkGreen),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = ButtonGreen)
            ) {
                Text("Save", color = TextWhite, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
@Composable
fun HeaderWithAvatar(username: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, end = 40.dp)
                .shadow(elevation = 4.dp, shape = RoundedCornerShape(12.dp)),
            color = DarkGreen,
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 12.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Silahkan Pilih avatar anda!",
                    color = TextWhite,
                    fontSize = 16.sp
                )
            }
        }
        Surface(
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = 16.dp)
                .shadow(elevation = 6.dp, shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp, bottomEnd = 12.dp)),
            color = ButtonGreen,
            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp, bottomEnd = 12.dp)
        ) {
            Text(
                text = username.ifEmpty { "Greeny" },
                color = TextWhite,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
            )
        }
        Image(
            painter = painterResource(id = R.drawable.cat_headphone),
            contentDescription = "Mascot",
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.TopEnd)
        )
    }
}

@Composable
fun AvatarItem(
    avatar: Avatar,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) ButtonGreen else Color.Transparent
    val context = LocalContext.current
    val imageId = remember(avatar.imageResName) {
        context.resources.getIdentifier(avatar.imageResName, "drawable", context.packageName)
    }

    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .width(180.dp)
            .height(220.dp)
            .border(4.dp, borderColor, RoundedCornerShape(20.dp))
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box {
            Image(

                painter = painterResource(id = if (imageId != 0) imageId else R.drawable.cat_thumb),
                contentDescription = avatar.name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, DarkGreen.copy(alpha = 0.8f))
                        )
                    )
            )
            Text(
                text = avatar.name,
                color = TextWhite,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            )
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_4")
@Composable
fun AvatarChooseScreenPreview() {
    AvatarChooseScreen(username = "Greeny", onNavigateBack = {}, onNavigateNext = {})
}