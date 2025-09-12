package com.example.greenfinity.features.auth.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.greenfinity.R
import com.example.greenfinity.ui.theme.DarkGreen
import com.example.greenfinity.ui.theme.LightBackgroundGreen
import com.example.greenfinity.ui.theme.TextWhite

@Composable
fun WelcomeScreen(onGetStarted: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGreen) // Menggunakan warna DarkGreen sebagai background utama
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(64.dp)) // Memberi jarak dari atas

            // Logo dan Welcome Text
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.logo_welcome), // Ganti dengan drawable logo Anda
                    contentDescription = "Greenfinity Logo",
                    modifier = Modifier.width(200.dp) // Sesuaikan ukuran logo
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Welcome to",
                    color = TextWhite,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Light
                )
                Text(
                    text = "Greenfinity",
                    color = TextWhite,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Gambar Maskot
            Image(
                painter = painterResource(id = R.drawable.mascot_welcome), // Ganti dengan drawable maskot Anda
                contentDescription = "Mascot",
                modifier = Modifier.size(250.dp) // Sesuaikan ukuran maskot
            )

            // Slogan
            Text(
                text = "Limitless effort to\nprotect the world",
                color = TextWhite.copy(alpha = 0.8f),
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                lineHeight = 28.sp // Memberi jarak antar baris
            )

            Spacer(modifier = Modifier.height(64.dp)) // Memberi jarak dari tombol
        }

        // Tombol "Get start"
        Button(
            onClick = onGetStarted,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .align(Alignment.BottomCenter) // Posisikan di bawah tengah
                .shadow(elevation = 8.dp, shape = CircleShape, ambientColor = Color.Black.copy(alpha = 0.5f)),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.LightGray.copy(alpha = 0.5f), // Warna abu-abu transparan
                contentColor = TextWhite
            ),
            contentPadding = PaddingValues(0.dp) // Hapus padding default
        ) {
            // Gradient di atas tombol agar terlihat seperti gambar
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.2f), // Lebih terang di atas
                                Color.Transparent // Transparan di bawah
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Get start",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_4")
@Composable
fun WelcomeScreenPreview() {
    MaterialTheme {
        WelcomeScreen(onGetStarted = {})
    }
}