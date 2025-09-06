package com.example.greenfinity.features.auth.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.greenfinity.ui.theme.DarkGreen
import com.example.greenfinity.ui.theme.TextWhite

@Composable
fun AuthBottomBar( // Namanya kita ubah menjadi lebih umum
    currentPage: Int,
    onBack: () -> Unit,
    onNext: () -> Unit,
    nextButtonText: String? = null
) {
    BottomAppBar(
        containerColor = DarkGreen,
        contentColor = TextWhite
    ) {
        IconButton(onClick = onBack) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }

        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.Center
        ) {
            // Page Indicator
            (0..2).forEach { index ->
                val color = if (index == currentPage) TextWhite else TextWhite.copy(alpha = 0.5f)
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(color)
                )
            }
        }

        if (nextButtonText != null) {
            TextButton(onClick = onNext) {
                Text(text = nextButtonText, color = TextWhite, fontSize = 16.sp)
                Spacer(modifier = Modifier.width(4.dp))
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next")
            }
        } else {
            IconButton(onClick = onNext) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next")
            }
        }
    }
}