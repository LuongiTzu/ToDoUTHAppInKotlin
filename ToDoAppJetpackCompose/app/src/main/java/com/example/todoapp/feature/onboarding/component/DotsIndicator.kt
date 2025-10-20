package com.example.todoapp.feature.onboarding.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DotsIndicator(total: Int, current: Int, size: Dp = 8.dp, space: Dp = 6.dp) {
    Row {
        repeat(total) { i ->
            val active = i == current
            Box(
                Modifier
                    .size(if (active) size * 1.6f else size)
                    .clip(CircleShape)
                    .background(
                        if (active) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.outlineVariant
                    )
            )
            if (i < total - 1) Spacer(Modifier.width(space))
        }
    }
}
