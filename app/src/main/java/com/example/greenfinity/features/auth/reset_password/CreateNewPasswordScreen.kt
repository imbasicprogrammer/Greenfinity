package com.example.greenfinity.features.auth.reset_password

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.greenfinity.features.viewmodels.ViewModelFactory
import com.example.greenfinity.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun CreateNewPasswordScreen(
    onPasswordSaved: () -> Unit,
    viewModel: ResetPasswordViewModel = viewModel(factory = ViewModelFactory(LocalContext.current))
) {
    var usernameOrEmail by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isNewPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val isFormValid by remember(usernameOrEmail, newPassword, confirmPassword) {
        derivedStateOf {
            usernameOrEmail.isNotBlank() &&
                    newPassword.length >= 6 &&
                    confirmPassword == newPassword
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBackgroundGreen)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStartPercent = 50, topEndPercent = 50))
                .background(MediumGreen)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(64.dp))

            Text(
                text = "Create New Password",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = DarkGreen,
                modifier = Modifier.fillMaxWidth(), // Tambahkan ini
                textAlign = TextAlign.Center // dan ini
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Please enter and confirm your\npassword",
                color = DarkGreen.copy(0.7f),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(48.dp))

            StyledTextField(value = usernameOrEmail, onValueChange = { usernameOrEmail = it }, placeholder = "Username/Email")
            Spacer(modifier = Modifier.height(16.dp))
            StyledTextField(
                value = newPassword,
                onValueChange = {
                    newPassword = it
                    passwordError = if (it.isNotEmpty() && it.length < 6) {
                        "Your password must be a minimum of 6 characters long."
                    } else {
                        null
                    }
                },
                placeholder = "New Password",
                isPassword = true,
                isPasswordVisible = isNewPasswordVisible,
                onVisibilityChange = { isNewPasswordVisible = !isNewPasswordVisible },
                isError = passwordError != null
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                passwordError?.let {
                    Text(
                        text = it, color = Color.Red, fontSize = 12.sp,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            StyledTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = "Confirm Password",
                isPassword = true,
                isPasswordVisible = isConfirmPasswordVisible,
                onVisibilityChange = { isConfirmPasswordVisible = !isConfirmPasswordVisible }
            )
            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = {
                    scope.launch {
                        val success = viewModel.updatePassword(usernameOrEmail, newPassword)
                        if (success) {
                            Toast.makeText(context, "Password berhasil diubah!", Toast.LENGTH_SHORT).show()
                            onPasswordSaved()
                        } else {
                            Toast.makeText(context, "User tidak ditemukan!", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                enabled = isFormValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .shadow(elevation = 8.dp, shape = CircleShape, ambientColor = Color.Black.copy(alpha = 0.5f)),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonGreen,
                    disabledContainerColor = ButtonGreen.copy(alpha = 0.5f)
                ),
                contentPadding = PaddingValues(0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color.White.copy(alpha = 0.3f), Color.Transparent)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Save Password", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextWhite)
                }
            }
        }
    }
}

@Composable
private fun StyledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isPassword: Boolean = false,
    isPasswordVisible: Boolean = false,
    onVisibilityChange: () -> Unit = {},
    isError: Boolean = false
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = DarkGreen.copy(alpha = 0.5f)) },

        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = DarkGreen.copy(alpha = 0.1f),
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
        singleLine = true,
        isError = isError,
        visualTransformation = if (isPassword && !isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Text),
        trailingIcon = {
            if (isPassword) {
                IconButton(onClick = onVisibilityChange) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Toggle password visibility",
                        tint = DarkGreen.copy(alpha = 0.7f)
                    )
                }
            }
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFF0F2EF),
            unfocusedContainerColor = Color(0xFFF0F2EF),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            focusedTextColor = DarkGreen,
            unfocusedTextColor = DarkGreen
        )
    )
}

@Preview(showBackground = true)
@Composable
fun CreateNewPasswordScreenPreview() {
    CreateNewPasswordScreen(onPasswordSaved = {})
}