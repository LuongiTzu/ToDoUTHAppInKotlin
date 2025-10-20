package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.ui.navigation.AppNavGraph
import com.example.todoapp.ui.theme.ToDoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { App() }
    }
}

@Composable
private fun App() {
    ToDoAppTheme {
        val nav = rememberNavController()
        AppNavGraph(nav)
    }
}
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AppPreview() {
    ToDoAppTheme {
        val nav = rememberNavController()
        AppNavGraph(nav)
    }
}