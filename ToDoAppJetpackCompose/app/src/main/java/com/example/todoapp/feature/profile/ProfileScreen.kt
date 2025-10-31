package com.example.todoapp.feature.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.todoapp.R
import com.example.todoapp.domain.profile.model.UserProfile
import com.example.todoapp.ui.theme.ToDoAppTheme
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(onBack: () -> Unit) {
    val vm: ProfileViewModel = viewModel(factory = ProfileViewModel.provideFactory())
    val state by vm.state.collectAsState()
    val snack = remember { SnackbarHostState() }

    ProfileContent(
        state = state,
        onBack = onBack,
        onSave = { p -> vm.save(p, onDone = onBack) },
        onChangeAvatar = { /* TODO: picker ảnh */ },
        snackbarHostState = snack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileContent(
    state: ProfileUiState,
    onBack: () -> Unit,
    onSave: (UserProfile) -> Unit,
    onChangeAvatar: () -> Unit,
    snackbarHostState: SnackbarHostState
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, null) }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        when {
            state.loading -> Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            state.error != null -> {
                LaunchedEffect(state.error) { snackbarHostState.showSnackbar(state.error) }
                Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                    Text("Đã xảy ra lỗi. Vui lòng thử lại.")
                }
            }
            else -> {
                val p = state.profile ?: UserProfile()
                var name by remember(p) { mutableStateOf(p.name) }
                var email by remember(p) { mutableStateOf(p.email) }
                var dob by remember(p) { mutableStateOf(p.dob) }
                var showDobPicker by remember { mutableStateOf(false) }

                Column(
                    Modifier.fillMaxSize().padding(padding).padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.height(16.dp))

                    // Avatar + camera overlay
                    Box(Modifier.size(104.dp), contentAlignment = Alignment.BottomEnd) {
                        val avatarMod = Modifier.size(104.dp).clip(CircleShape)
                        if (!p.avatarUrl.isNullOrBlank()) {
                            AsyncImage(model = p.avatarUrl, contentDescription = null, modifier = avatarMod)
                        } else {
                            Image(
                                painter = painterResource(p.avatarRes ?: R.drawable.ic_launcher_foreground),
                                contentDescription = null, modifier = avatarMod
                            )
                        }
                        FilledIconButton(onClick = onChangeAvatar, modifier = Modifier.size(32.dp).offset(x = 6.dp, y = 6.dp)) {
                            Icon(Icons.Outlined.CameraAlt, contentDescription = null)
                        }
                    }

                    Spacer(Modifier.height(24.dp))

                    OutlinedTextField(
                        value = name, onValueChange = { name = it },
                        label = { Text("Name") }, modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(12.dp))

                    OutlinedTextField(
                        value = email, onValueChange = {}, enabled = false,
                        label = { Text("Email") }, modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(12.dp))

                    OutlinedTextField(
                        value = dob, onValueChange = {}, readOnly = true,
                        label = { Text("Date of Birth") },
                        trailingIcon = {
                            IconButton(onClick = { showDobPicker = true }) {
                                Icon(Icons.Outlined.CalendarMonth, contentDescription = null)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (showDobPicker) {
                        DatePickerDialog(
                            onDismissRequest = { showDobPicker = false },
                            confirmButton = { TextButton(onClick = { showDobPicker = false }) { Text("OK") } },
                            dismissButton = { TextButton(onClick = { showDobPicker = false }) { Text("Cancel") } }
                        ) {
                            val statePicker = rememberDatePickerState(
                                initialSelectedDateMillis = dob.takeIf { it.isNotBlank() }?.let { parseDob(it) }
                            )
                            DatePicker(state = statePicker)
                            LaunchedEffect(statePicker.selectedDateMillis) {
                                statePicker.selectedDateMillis?.let { dob = formatDob(it) }
                            }
                        }
                    }

                    Spacer(Modifier.weight(1f))
                    Button(
                        onClick = { onSave(UserProfile(name = name, email = email, dob = dob, avatarUrl = p.avatarUrl, avatarRes = p.avatarRes)) },
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        shape = MaterialTheme.shapes.extraLarge
                    ) { Text("Back") }
                    Spacer(Modifier.height(16.dp))
                }
            }
        }
    }
}

private fun formatDob(millis: Long): String =
    SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(millis)

private fun parseDob(dob: String): Long? = try {
    SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(dob)?.time
} catch (_: Exception) { null }

@Preview(showSystemUi = true)
@Composable
private fun ProfilePreview() = ToDoAppTheme {
    ProfileContent(
        state = ProfileUiState(loading = false, profile = UserProfile(name = "Melissa", email = "mel@ex.com", dob = "01/01/2000")),
        onBack = {}, onSave = {}, onChangeAvatar = {}, snackbarHostState = remember { SnackbarHostState() }
    )
}
