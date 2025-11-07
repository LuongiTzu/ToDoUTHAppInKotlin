package com.example.todoapp.feature.task.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.domain.task.model.TaskResponse

@Composable
fun TaskItemCard(task: TaskResponse, onClick: () -> Unit) {
    var checked by remember { mutableStateOf(task.status.equals("Completed", true)) }

    val bgColor = when (task.status.lowercase()) {
        "in progress" -> Color(0xFFFFCDD2)
        "pending" -> Color(0xFFF0F4C3)
        "completed" -> Color(0xFFC8E6C9)
        "overdue" -> Color(0xFFFFEBEE)
        else -> Color(0xFFBBDEFB)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            Modifier
                .background(bgColor)
                .padding(14.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = checked, onCheckedChange = { checked = it })
                Spacer(Modifier.width(8.dp))
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
                )
            }

            Spacer(Modifier.height(4.dp))
            Text(task.description, fontSize = 14.sp, color = Color.DarkGray)
            Spacer(Modifier.height(8.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    "Status: ${task.status}",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    color = Color.Black
                )
                Text(task.dueDate?.substringBefore("T") ?: "-", fontSize = 13.sp, color = Color.Gray)
            }
        }
    }
}
