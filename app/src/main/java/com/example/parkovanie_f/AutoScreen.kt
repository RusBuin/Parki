package com.example.parkovanie_f

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AutoScreen() {
    val parkingSlots = remember {
        mutableStateListOf(
            ParkingSlot(1, isOccupied = false, isSelected = false, isUnavailable = false),
            ParkingSlot(2, isOccupied = true, isSelected = false, isUnavailable = false),
            ParkingSlot(3, isOccupied = false, isSelected = false, isUnavailable = false),
            ParkingSlot(4, isOccupied = false, isSelected = false, isUnavailable = true), // Недоступное место
            ParkingSlot(5, isOccupied = false, isSelected = false, isUnavailable = false),
            ParkingSlot(6, isOccupied = false, isSelected = false, isUnavailable = false),
            ParkingSlot(7, isOccupied = true, isSelected = false, isUnavailable = false),
            ParkingSlot(8, isOccupied = true, isSelected = false, isUnavailable = false)
        )
    }
    val isSlotSelected = parkingSlots.any { it.isSelected }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
            .padding(16.dp)
    ) {
        Text(
            text = "",
            style = androidx.compose.ui.text.TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                color = Color.White
            ),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Левая сторона
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.Start
            ) {
                parkingSlots.subList(0, 4).forEach { slot ->
                    ParkingSlotView(slot) { updateSlot(slot, parkingSlots) }
                }
            }
            // Центральные полосы
            Box(
                modifier = Modifier
                    .width(16.dp) // Ширина полосы
                    .height(550.dp)
                    .background(Color.Black) // Цвет полосы
            )

            // Правая сторона
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.End
            ) {
                parkingSlots.subList(4, 8).forEach { slot ->
                    ParkingSlotView(slot) { updateSlot(slot, parkingSlots) }
                }
            }

        }
        Button(
            onClick = { /* Логика подтверждения выбора */ },
            enabled = isSlotSelected, // Кнопка активна только при выбранном месте
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isSlotSelected) Color(0xFF6200EE) else Color.Gray // Меняем цвет в зависимости от состояния
            ),
            modifier = Modifier
                .padding(top = 50.dp, end = 16.dp) // Отступ сверху и справа
                .align(Alignment.End) // Размещаем кнопку ближе к правому краю
                .size(width = 170.dp, height = 70.dp) // Увеличиваем размер кнопки
        ) {
            Text(
                text = "Accept",
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 24.sp,
                    color = Color.White
                )
            )
        }
    }
}

@Composable
fun ParkingSlotView(slot: ParkingSlot, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(120.dp)
            .background(
                when {
                    slot.isUnavailable -> Color.Red // Красный для недоступного
                    slot.isOccupied -> Color(0xFF6200EE) // Фиолетовый для занятого
                    slot.isSelected -> Color(0xFFFFD700) // Желтый для выбранного
                    else -> Color.Gray // Серый для свободного
                },
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(enabled = !slot.isOccupied && !slot.isUnavailable, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        when {
            slot.isUnavailable -> {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Недоступно",
                    tint = Color.White,
                    modifier = Modifier.size(60.dp)
                )
            }
            slot.isOccupied -> {
                Icon(
                    painter = painterResource(id = R.drawable.car),
                    contentDescription = "Occupied",
                    tint = Color.White,
                    modifier = Modifier.size(60.dp)
                )
            }
            else -> {
                Text(
                    text = if (slot.isSelected) "Selected" else "Slot ${slot.number}",
                    color = Color.White,
                    style = androidx.compose.ui.text.TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

fun updateSlot(selectedSlot: ParkingSlot, parkingSlots: MutableList<ParkingSlot>) {
    val index = parkingSlots.indexOf(selectedSlot)
    if (index != -1) {
        parkingSlots.forEach { it.isSelected = false } // Снимаем выделение со всех слотов
        parkingSlots[index] = selectedSlot.copy(isSelected = true) // Обновляем выбранный слот
    }
}

// Модель данных для парковочного места
data class ParkingSlot(
    val number: Int,
    val isOccupied: Boolean,
    var isSelected: Boolean,
    val isUnavailable: Boolean // Новый флаг для недоступных мест
)
