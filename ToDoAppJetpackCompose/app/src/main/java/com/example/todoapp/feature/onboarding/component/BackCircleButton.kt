package com.example.todoapp.feature.onboarding.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun BackCircleButton(
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bg = if (enabled) MaterialTheme.colorScheme.primary
    else MaterialTheme.colorScheme.outlineVariant
    val tint = MaterialTheme.colorScheme.onPrimary

    Box(
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(bg)
            .let { if (enabled) it.clickable(onClick = onClick) else it },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Rounded.ArrowBack,
            contentDescription = "Back",
            tint = tint
        )
    }
}
