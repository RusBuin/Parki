package com.example.parkovanie_f

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class AutoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val selectedTime = intent.getStringExtra("SELECTED_TIME") ?: "Not selected"

        setContent {
            AutoScreen()
        }
    }
}