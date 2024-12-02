package com.example.parkovanie_f

import android.app.TimePickerDialog
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*

@Composable
fun TimeSelectionScreen() {
    var selectedTime by remember { mutableStateOf("") }
    var selectedDuration by remember { mutableStateOf(1) } // Default duration: 1 hour
    var isTimeDialogOpen by remember { mutableStateOf(false) }
    var isDurationDialogOpen by remember { mutableStateOf(false) }
    var isTimeSelected by remember { mutableStateOf(false) }

    if (isTimeDialogOpen) {
        TimePickerDialogComponent(
            onTimeSelected = { selectedHour, selectedMinute ->
                selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                isTimeDialogOpen = false
                isTimeSelected = true
            },
            onDialogDismissed = { isTimeDialogOpen = false }
        )
    }

    if (isDurationDialogOpen) {
        DurationPickerDialogComponent(
            currentDuration = selectedDuration,
            onDurationSelected = { duration ->
                selectedDuration = duration
                isDurationDialogOpen = false
            },
            onDialogDismissed = { isDurationDialogOpen = false }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF212121)) // Dark background
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Reserve Your Spot",
            style = androidx.compose.ui.text.TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp,
                color = Color.White
            ),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Time Selection Button
        Button(
            onClick = { isTimeDialogOpen = true },
            modifier = Modifier
                .size(width = 260.dp, height = 70.dp)
                .shadow(8.dp, shape = RoundedCornerShape(16.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)) // Purple color
        ) {
            Text(
                text = "Select Time",
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Duration Selection Button
        Button(
            onClick = { isDurationDialogOpen = true },
            modifier = Modifier
                .size(width = 260.dp, height = 70.dp)
                .shadow(8.dp, shape = RoundedCornerShape(16.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03DAC5)) // Teal color
        ) {
            Text(
                text = "Select Duration",
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Display Selected Time
        if (selectedTime.isNotEmpty()) {
            Text(
                text = "Selected Time: $selectedTime",
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Light,
                    color = Color(0xFFE0E0E0)
                )
            )
        }

        // Display Selected Duration
        if (isTimeSelected) {
            Text(
                text = "Duration: $selectedDuration hour(s)",
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Light,
                    color = Color(0xFFE0E0E0)
                ),
                modifier = Modifier.padding(top = 16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Confirm Button
            val context = LocalContext.current
            Button(
                onClick = {
                    Log.d("TimeSelectionScreen", "Selected time: $selectedTime, Duration: $selectedDuration hour(s)")
                },
                modifier = Modifier
                    .size(width = 260.dp, height = 70.dp)
                    .shadow(8.dp, shape = RoundedCornerShape(16.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)) // Green color
            ) {
                Text(
                    text = "Confirm",
                    style = androidx.compose.ui.text.TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                )
            }
        }
    }
}

@Composable
fun TimePickerDialogComponent(onTimeSelected: (Int, Int) -> Unit, onDialogDismissed: () -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    TimePickerDialog(
        context,
        { _, selectedHour, selectedMinute ->
            onTimeSelected(selectedHour, selectedMinute)
        },
        hour,
        minute,
        true
    ).apply {
        setOnDismissListener { onDialogDismissed() }
    }.show()
}

@Composable
fun DurationPickerDialogComponent(
    currentDuration: Int,
    onDurationSelected: (Int) -> Unit,
    onDialogDismissed: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDialogDismissed() },
        title = {
            Text(text = "Select Duration", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        },
        text = {
            Column {
                for (i in 1..24) {
                    Text(
                        text = "$i hour(s)",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onDurationSelected(i) }
                            .padding(vertical = 8.dp),
                        fontSize = 18.sp,
                        color = if (i == currentDuration) Color(0xFF6200EE) else Color.Black // Highlight selected duration
                    )
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = { onDialogDismissed() }) {
                Text(text = "Cancel")
            }
        }
    )
}
