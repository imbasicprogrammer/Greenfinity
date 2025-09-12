package com.example.greenfinity.features.auth.user_form

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.greenfinity.R
import com.example.greenfinity.features.auth.components.AuthBottomBar
import com.example.greenfinity.ui.theme.*
import java.util.Calendar
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import com.example.greenfinity.features.auth.components.SuccessPopup



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserFormScreen(
    initialUsername: String,
    onNavigateBack: () -> Unit,
    onNavigateNext: (String) -> Unit
) {
    var username by remember { mutableStateOf(initialUsername) }
    var bio by remember { mutableStateOf("") }
    var selectedDateText by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf<String?>(null) }

    var showSuccessPopup by remember { mutableStateOf(false) }

    if (showSuccessPopup) {
        SuccessPopup(
            message = "Nice! Your profile is set up.",
            onDismiss = {
                showSuccessPopup = false
                onNavigateNext(username)
            }
        )
    }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            selectedDateText = "$dayOfMonth/${month + 1}/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            AuthBottomBar(
                currentPage = 0,
                onBack = onNavigateBack,
                onNext = {
                    showSuccessPopup = true
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Text("Edit Profile", color = DarkGreen, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(32.dp))

            InputLabel("Username :")
            CustomTextField(value = username, onValueChange = { username = it })
            Spacer(modifier = Modifier.height(16.dp))
            InputLabel("Bio :")
            CustomTextField(value = bio, onValueChange = { bio = it }, minLines = 3, singleLine = false)
            Spacer(modifier = Modifier.height(16.dp))
            InputLabel("Date of birth :")
            DateInputField(selectedDateText = selectedDateText) { datePickerDialog.show() }
            Spacer(modifier = Modifier.height(16.dp))
            InputLabel("Gender :")
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                GenderCheckbox(text = "Male", isSelected = gender == "Male") { gender = "Male" }
                GenderCheckbox(text = "Female", isSelected = gender == "Female") { gender = "Female" }
            }
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    showSuccessPopup = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .shadow(elevation = 8.dp, shape = CircleShape, spotColor = DarkGreen),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = ButtonGreen)
            ) {
                Text("Save", color = TextWhite, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            }
            Spacer(modifier = Modifier.weight(1f))
            ChatBubbleWithAvatar(
                username = username,
                message = "Kamu adalah harapan baru bagi masa depan bumi",
                avatarResId = R.drawable.cat_headphone
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}


@Composable
fun InputLabel(text: String) {
    Text(
        text = text, color = DarkGreen.copy(alpha = 0.8f), fontSize = 16.sp,
        fontWeight = FontWeight.Medium, modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
    )
}

@Composable
fun CustomTextField(
    value: String, onValueChange: (String) -> Unit, minLines: Int = 1, singleLine: Boolean = true
) {
    Surface(
        shape = RoundedCornerShape(24.dp),
        color = DarkGreen,
        modifier = Modifier.shadow(elevation = 4.dp, shape = RoundedCornerShape(24.dp))
    ) {
        TextField(
            value = value, onValueChange = onValueChange, singleLine = singleLine, minLines = minLines,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent,
                cursorColor = TextWhite, focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent, focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite
            ),
            textStyle = TextStyle(color = TextWhite, fontSize = 16.sp),
        )
    }
}

@Composable
fun DateInputField(selectedDateText: String, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(24.dp),
        color = DarkGreen,
        modifier = Modifier
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(24.dp))
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (selectedDateText.isEmpty()) "DD/MM/YYYY" else selectedDateText,
                color = if (selectedDateText.isEmpty()) TextWhite.copy(alpha = 0.7f) else TextWhite,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = "Select Date", tint = TextWhite
            )
        }
    }
}

@Composable
fun RowScope.GenderCheckbox(text: String, isSelected: Boolean, onSelect: () -> Unit) {
    Surface(
        modifier = Modifier
            .weight(1f)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onSelect),
        color = DarkGreen,
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isSelected,
                onCheckedChange = { onSelect() },
                colors = CheckboxDefaults.colors(
                    checkedColor = ButtonGreen,
                    uncheckedColor = TextWhite.copy(alpha = 0.7f),
                    checkmarkColor = DarkGreen
                )
            )
            Spacer(Modifier.width(8.dp))
            Text(text = text, color = TextWhite, fontSize = 16.sp)
        }
    }
}

@Composable
fun ChatBubbleWithAvatar(username: String, message: String, avatarResId: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = DarkGreen,
            modifier = Modifier
                .padding(end = 40.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp))
        ) {
            Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 12.dp)) {
                Surface(
                    color = ButtonGreen,
                    shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp, bottomEnd = 12.dp),
                ) {
                    Text(
                        text = username.ifEmpty { "Greeny" }, color = TextWhite, fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = message, color = TextWhite, fontSize = 14.sp)
            }
        }
        Image(
            painter = painterResource(id = avatarResId),
            contentDescription = "Avatar",
            modifier = Modifier
                .size(90.dp)
                .align(Alignment.BottomEnd)
        )
    }
}


@Preview(showBackground = true, device = "id:pixel_4")
@Composable
fun UserFormScreenPreview() {
    UserFormScreen(
        initialUsername = "Greeny",
        onNavigateBack = {},
        onNavigateNext = {}
    )
}