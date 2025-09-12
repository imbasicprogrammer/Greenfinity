package com.example.greenfinity.features.auth.choice

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.greenfinity.ui.theme.DarkGreen
import com.example.greenfinity.ui.theme.MediumGreen
import com.example.greenfinity.ui.theme.TextWhite

@Composable
fun AuthChoiceScreen(
    onRegisterClicked: () -> Unit,
    onLoginClicked: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MediumGreen
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Greenfinity",
                color = TextWhite,
                fontSize = 44.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(128.dp))

            // Tombol Register
            AuthButton(
                text = "Register",
                onClick = onRegisterClicked
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Tombol Login
            AuthButton(
                text = "Login",
                onClick = onLoginClicked
            )
        }
    }
}


@Composable
private fun AuthButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = DarkGreen
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Text(
            text = text,
            color = TextWhite,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
    }
}


@Preview(showBackground = true, device = "id:pixel_4")
@Composable
fun AuthChoiceScreenPreview() {
    AuthChoiceScreen(onRegisterClicked = {}, onLoginClicked = {})
}