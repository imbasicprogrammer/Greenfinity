package com.example.greenfinity.features.auth.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.greenfinity.R
import com.example.greenfinity.ui.theme.DarkGreen
import com.example.greenfinity.ui.theme.TextWhite

@Composable
fun WelcomeScreen(
    onGetStartedClicked: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = DarkGreen
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 40.dp)
                // Beri padding vertikal untuk jarak atas dan bawah
                .padding(vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            // Ganti arrangement untuk kontrol yang lebih baik
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // 1. Kelompokkan Logo dan Maskot dalam satu Column
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.logo_welcome),
                    contentDescription = "Greenfinity Logo",
                    modifier = Modifier.fillMaxWidth(0.9f)
                )
                // Beri jarak kecil antara logo dan maskot agar lebih dekat
                Spacer(modifier = Modifier.height(16.dp))

                Image(
                    painter = painterResource(id = R.drawable.mascot_welcome),
                    contentDescription = "Mascot",
                    modifier = Modifier.height(200.dp)
                )
            }

            // 2. Tagline
            Text(
                text = "Limitless effort to protect the world",
                color = TextWhite,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                lineHeight = 26.sp
            )

            // 3. Tombol "Get start"
            Button(
                onClick = onGetStartedClicked,
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE0E0E0),
                    contentColor = DarkGreen
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    // Spacer pemberat dihapus, posisi tombol diatur oleh Arrangement.SpaceBetween
                    .shadow(elevation = 8.dp, shape = CircleShape)
            ) {
                Text(
                    text = "Get start",
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_4")
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen(onGetStartedClicked = {})
}