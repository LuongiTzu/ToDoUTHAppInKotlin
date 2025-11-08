package com.example.todoapp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.todoapp.ui.navigation.Dest

@Composable
fun MyBottomBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(horizontal = 32.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        // --- Nền của bottom bar ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .clip(RoundedCornerShape(28.dp))
                .background(Color.White)
                .align(Alignment.BottomCenter)
        )

        // --- Các icon ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // 🏠 Home
            IconButton(onClick = {
                navController.navigate(Dest.TaskList.route) {
                    popUpTo(Dest.TaskList.route) { inclusive = false }
                }
            }) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Home",
                    tint = if (currentRoute?.startsWith("tasks") == true)
                        Color(0xFF2196F3)
                    else Color.Gray
                )
            }

            // 📅 Calendar
            IconButton(onClick = { /* TODO: sau này thêm */ }) {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = "Calendar",
                    tint = Color.Gray
                )
            }

            Spacer(Modifier.width(40.dp)) // chừa chỗ cho nút giữa

            // 📄 Notes
            IconButton(onClick = { /* TODO: sau này thêm */ }) {
                Icon(
                    imageVector = Icons.Filled.Description,
                    contentDescription = "Notes",
                    tint = Color.Gray
                )
            }

            // ⚙️ Settings
            IconButton(onClick = {
                navController.navigate(Dest.Profile.route) {
                    popUpTo(Dest.TaskList.route) { inclusive = false }
                }
            }) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Settings",
                    tint = if (currentRoute == Dest.Profile.route)
                        Color(0xFF2196F3)
                    else Color.Gray
                )
            }
        }

        // --- Nút ➕ giữa ---
        FloatingActionButton(
            onClick = { /* TODO: mở form thêm task */ },
            containerColor = Color(0xFF2196F3),
            shape = CircleShape,
            modifier = Modifier
                .size(56.dp)
                .align(Alignment.TopCenter)
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add",
                tint = Color.White
            )
        }
    }
}
