package com.example.todoapp.feature.task.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.ui.theme.ToDoAppTheme

@Composable
fun EmptyView() {
    Box(
        modifier = Modifier
            .fillMaxSize().offset(y= -190.dp),
        contentAlignment = Alignment.Center
    ) {
        // Nền xám bo góc
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFF2F2F2)) // xám nhạt
                .padding(vertical = 32.dp, horizontal = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Icon ảnh bạn gửi
                Image(
                    painter = painterResource(id = R.drawable.notaskicon),
                    contentDescription = "No Tasks",
                    modifier = Modifier
                        .size(80.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(Modifier.height(16.dp))
                Text(
                    "No Tasks Yet!",
                    style = MaterialTheme.typography.titleMedium.copy(color = Color.Black)
                )
                Text(
                    "Stay productive—add something to do",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun EmptyViewPreview() = ToDoAppTheme {
    EmptyView()
}
