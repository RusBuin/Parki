package com.example.parkovanie_f

import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*

@Composable
fun СasScreen(context: Context) {
    var selectedTime by remember { mutableStateOf("") } // Для хранения выбранного времени

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
            .padding(16.dp)
    ) {
        Text(
            text = "Выбор времени резервации",
            style = androidx.compose.ui.text.TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                color = Color.White
            ),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Кнопка для выбора времени
        Button(
            onClick = {
                CasDialog(context) { selectedHour, selectedMinute ->
                    selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)),
            modifier = Modifier
                .padding(top = 16.dp)
                .align(Alignment.CenterHorizontally)
                .size(width = 240.dp, height = 60.dp) // Размер кнопки
        ) {
            Text(
                text = "Выбрать время",
                style = androidx.compose.ui.text.TextStyle(fontSize = 18.sp, color = Color.White)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Отображение выбранного времени
        if (selectedTime.isNotEmpty()) {
            Text(
                text = "Выбранное время: $selectedTime",
                style = androidx.compose.ui.text.TextStyle(fontSize = 20.sp, color = Color.White),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

fun CasDialog(context: Context, onTimeSelected: (Int, Int) -> Unit) {
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    TimePickerDialog(
        context,
        { _, selectedHour, selectedMinute ->
            onTimeSelected(selectedHour, selectedMinute) // Возврат выбранного времени
        },
        hour,
        minute,
        true // Использовать 24-часовой формат
    ).show()
}
