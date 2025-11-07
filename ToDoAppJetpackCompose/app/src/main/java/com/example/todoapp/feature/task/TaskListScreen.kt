package com.example.todoapp.feature.task

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.todoapp.R
import com.example.todoapp.feature.task.component.EmptyView
import com.example.todoapp.feature.task.component.TaskItemCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    navController: NavController,
    vm: TaskViewModel = viewModel()   // ⬅️ bỏ provideFactory()
) {
    val tasks by vm.tasks.collectAsState()
    val isLoading by vm.isLoading.collectAsState()

    LaunchedEffect(Unit) { vm.loadTasks() }

    Scaffold(topBar = { TaskListHeader() }) { padding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            when {
                isLoading -> Box(Modifier.fillMaxSize(), Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF1976D2))
                }
                tasks.isEmpty() -> EmptyView()
                else -> LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(tasks) { task ->
                        TaskItemCard(task = task) {
                            navController.navigate("taskDetail/${task.id}")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TaskListHeader() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = 36.dp, start = 20.dp, end = 20.dp, bottom = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.uthlogin),
                contentDescription = "UTH Logo",
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(Modifier.width(12.dp))
            Column {
                Text(
                    buildAnnotatedString {
                        withStyle(SpanStyle(color = Color.Black, fontWeight = FontWeight.Bold)) {
                            append("Smart")
                        }
                        withStyle(SpanStyle(color = Color(0xFF1976D2), fontWeight = FontWeight.Bold)) {
                            append("Tasks")
                        }
                    },
                    fontSize = 26.sp
                )
                Text(
                    "A simple and efficient to-do app",
                    color = Color.Gray,
                    fontSize = 13.sp
                )
            }
        }

        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = "Notification",
            tint = Color(0xFFFFC107),
            modifier = Modifier.size(30.dp)
        )
    }
}
