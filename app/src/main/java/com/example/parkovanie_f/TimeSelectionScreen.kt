package com.example.parkovanie_f

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*

@Composable
fun TimeSelectionScreen() {
    var selectedTime by remember { mutableStateOf("") } // For storing the selected time
    var isDialogOpen by remember { mutableStateOf(false) } // For tracking the dialog state
    var isTimeSelected by remember { mutableStateOf(false) } // For tracking if the time is selected

    // If the dialog is open, show it
    if (isDialogOpen) {
        TimePickerDialogComponent(
            onTimeSelected = { selectedHour, selectedMinute ->
                selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                isDialogOpen = false // Close the dialog after time selection
                isTimeSelected = true // Mark that time was selected
            },
            onDialogDismissed = {
                isDialogOpen = false // Close the dialog without selecting time
            }
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
            text = "Select Reservation Time",
            style = androidx.compose.ui.text.TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                color = Color.White
            ),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Button to choose time
        Button(
            onClick = { isDialogOpen = true },
            modifier = Modifier
                .padding(top = 16.dp)
                .size(width = 240.dp, height = 60.dp)
                .background(Color(0xFF6200EE), shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
        ) {
            Text(
                text = "Select Time",
                style = androidx.compose.ui.text.TextStyle(fontSize = 18.sp, color = Color.White)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display the selected time
        if (selectedTime.isNotEmpty()) {
            Text(
                text = "Selected Time: $selectedTime",
                style = androidx.compose.ui.text.TextStyle(fontSize = 20.sp, color = Color.White),
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }

        // "Next" button appears after time is selected, but doesn't trigger any navigation now
        if (isTimeSelected) {
            Button(
                onClick = { /* Do nothing or some other action */ },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .size(width = 240.dp, height = 60.dp)
                    .background(Color(0xFF03DAC5), shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03DAC5))
            ) {
                Text(
                    text = "Next",
                    style = androidx.compose.ui.text.TextStyle(fontSize = 18.sp, color = Color.White)
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

    // 24-hour format time picker
    TimePickerDialog(
        context,
        { _, selectedHour, selectedMinute ->
            onTimeSelected(selectedHour, selectedMinute) // Return selected time
        },
        hour,
        minute,
        true // Use 24-hour format
    ).apply {
        setOnDismissListener {
            onDialogDismissed() // Close the dialog without selecting time
        }
    }.show()
}
