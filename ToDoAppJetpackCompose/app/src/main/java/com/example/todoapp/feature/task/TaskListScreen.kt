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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.R
import com.example.todoapp.feature.task.component.EmptyView
import com.example.todoapp.feature.task.component.TaskItemCard
import com.example.todoapp.ui.component.MyBottomBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    navController: NavController,
    vm: TaskViewModel = viewModel()
) {
    val tasks by vm.tasks.collectAsState()
    val isLoading by vm.isLoading.collectAsState()
    val shouldReload by vm.shouldReload.collectAsState()

    // 🔹 Lần đầu vào: load danh sách
    LaunchedEffect(Unit) { vm.loadTasks() }

    // 🔹 Khi Detail xóa task → reload lại danh sách
    LaunchedEffect(shouldReload) {
        if (shouldReload) {
            vm.loadTasks()
            vm.resetReloadFlag()
        }
    }

    Scaffold(
        topBar = { TaskListHeader() },
        bottomBar = { MyBottomBar(navController) }
    ) { padding ->
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
                            // 🔹 Điều hướng đúng route “task/{id}”
                            navController.navigate("taskDetail/${task.id}")                        }
                    }
                }
            }
        }
    }
}

/* ================= HEADER ================= */
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
                    text="SmartTasks",
                    fontSize = 26.sp,
                    color = Color(0xFF1976D2),
                    fontWeight = FontWeight.Bold
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

/* ================= PREVIEW ================= */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewTaskListScreen() {
    val navController = rememberNavController()

    val fakeTasks = listOf(
        com.example.todoapp.domain.task.model.TaskResponse(
            id = 1,
            title = "Complete Android Project",
            description = "Finish UI and connect to API",
            status = "In Progress",
            priority = "High",
            category = "Work",
            dueDate = "2024-11-20T09:00:00Z"
        ),
        com.example.todoapp.domain.task.model.TaskResponse(
            id = 2,
            title = "Submit Report",
            description = "Prepare and submit project report",
            status = "Pending",
            priority = "Medium",
            category = "Education",
            dueDate = "2024-11-22T09:00:00Z"
        )
    )

    Scaffold(
        topBar = { TaskListHeader() },
        bottomBar = { MyBottomBar(navController) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(fakeTasks) { task ->
                com.example.todoapp.feature.task.component.TaskItemCard(task = task) {}
            }
        }
    }
}
