package com.example.todoapp.feature.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todoapp.R
import com.example.todoapp.ui.navigation.Dest
import com.example.todoapp.ui.theme.ToDoAppTheme
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController? = null,
    onDone: () -> Unit = {}
) {
    LaunchedEffect(Unit) {
        delay(1200)

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // ✅ Nếu đã đăng nhập → chuyển thẳng TaskList
            navController?.navigate(Dest.TaskList.route) {
                popUpTo(0)
            }
        } else {
            // ✅ Nếu chưa → chuyển sang onboarding
            onDone()
        }
    }

    // UI splash
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(R.drawable.logouth),
                contentDescription = "Logo",
                modifier = Modifier.size(96.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = "UTH SmartTasks",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashPreview() = ToDoAppTheme { SplashScreen() }
