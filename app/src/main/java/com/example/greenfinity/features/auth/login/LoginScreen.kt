package com.example.greenfinity.features.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
fun LoginScreen(
    onLoginClicked: (String, String) -> Unit,
    onNavigateToRegister: () -> Unit,
    onForgotPasswordClicked: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = painterResource(id = R.drawable.mascot_welcome),
            contentDescription = "Mascot",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 40.dp)
                .size(160.dp)
        )

        Card(
            modifier = Modifier
                .padding(top = 180.dp)
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = MediumGreen),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_welcome),
                    contentDescription = "Logo",
                    modifier = Modifier.fillMaxWidth(0.7f)
                )

                Spacer(modifier = Modifier.height(32.dp))

                AuthTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Email / Username",
                    leadingIcon = Icons.Default.Person,
                    keyboardType = KeyboardType.Email
                )
                Spacer(modifier = Modifier.height(16.dp))
                AuthTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Password",
                    leadingIcon = Icons.Default.Lock,
                    isPasswordField = true
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = rememberMe,
                        onCheckedChange = { rememberMe = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = DarkGreen,
                            uncheckedColor = DarkGreen.copy(alpha = 0.7f)
                        )
                    )
                    Text("Remember me", color = DarkGreen.copy(alpha = 0.8f))
                }
                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {

                        val cleanEmail = email.trim()
                        val cleanPassword = password.trim()
                        onLoginClicked(cleanEmail, cleanPassword)
                    },
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
                    Text("Login", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }



                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    "Forgot Password",
                    color = DarkGreen.copy(alpha = 0.8f),
                    modifier = Modifier.clickable(onClick = onForgotPasswordClicked)
                )
            }
        }
    }
}


@Preview(showBackground = true, device = "id:pixel_4")
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        onLoginClicked = { _, _ -> },
        onNavigateToRegister = {},
        onForgotPasswordClicked = {}
    )
}