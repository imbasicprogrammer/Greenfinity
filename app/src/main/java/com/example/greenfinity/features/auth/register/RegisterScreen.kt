package com.example.greenfinity.features.auth.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.greenfinity.R
import com.example.greenfinity.features.auth.components.AuthTextField
import com.example.greenfinity.ui.theme.*

@Composable
fun RegisterScreen(
    onRegisterClicked: (String, String, String) -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Latar belakang putih
    ) {
        // Maskot di bagian atas, sedikit di bawah posisi logo
        Image(
            painter = painterResource(id = R.drawable.mascot_welcome),
            contentDescription = "Mascot",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 80.dp)
                .size(150.dp)
        )

        // Card Form
        Card(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .offset(y = 80.dp), // Geser ke bawah
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = MediumGreen),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Create Account",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkGreen
                )
                Spacer(modifier = Modifier.height(24.dp))

                // Menambahkan leadingIcon untuk setiap field
                AuthTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = "Full Name",
                    leadingIcon = Icons.Default.Person // <-- IKON DITAMBAHKAN
                )
                Spacer(modifier = Modifier.height(16.dp))
                AuthTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Email",
                    leadingIcon = Icons.Default.Email, // <-- IKON DITAMBAHKAN
                    keyboardType = KeyboardType.Email
                )
                Spacer(modifier = Modifier.height(16.dp))
                AuthTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Password",
                    leadingIcon = Icons.Default.Lock, // <-- IKON DITAMBAHKAN
                    isPasswordField = true
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Tombol Register dengan gaya baru
                Button(
                    onClick = { onRegisterClicked(name, email, password) },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE0E0E0),
                        contentColor = DarkGreen
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .shadow(elevation = 8.dp, shape = CircleShape)
                ) {
                    Text("Register", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Teks untuk navigasi ke halaman Login
                Row {
                    Text("Already have an account? ", color = DarkGreen.copy(alpha = 0.7f))
                    Text(
                        "Sign In",
                        color = DarkGreen,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable(onClick = onNavigateToLogin)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_4")
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(onRegisterClicked = { _, _, _ -> }, onNavigateToLogin = {})
}