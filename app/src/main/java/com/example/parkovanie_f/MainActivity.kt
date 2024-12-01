package com.example.parkovanie_f

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.parkovanie_f.ui.theme.Parkovanie_fTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Логирование этапа создания Activity
        Log.d("MainActivity", "onCreate: MainActivity создана")

        enableEdgeToEdge()

        // Установить SplashScreen
        setContent {
            Parkovanie_fTheme {
                Log.d("MainActivity", "onCreate: Отображение SplashScreen")
                FullScreenImage()
            }
        }

        // Задержка перед переходом на HomeActivity
        Handler(Looper.getMainLooper()).postDelayed({
            Log.d("MainActivity", "postDelayed: Переход на HomeActivity")
            startActivity(Intent(this, HomeActivity::class.java))
            Log.d("MainActivity", "postDelayed: MainActivity завершена")
            finish() // Завершение SplashScreenActivity
        }, 200) // 200 миллисекунд задержка
    }
}

@Composable
fun FullScreenImage() {
    Log.d("FullScreenImage", "Отображение полноэкранного изображения")
    Image(
        painter = painterResource(id = R.drawable.ic_map), // Укажите свой ресурс изображения
        contentDescription = null,
        modifier = Modifier.fillMaxSize()
    )
}

@Preview(showBackground = true)
@Composable
fun FullScreenImagePreview() {
    Parkovanie_fTheme {
        Log.d("FullScreenImagePreview", "Предварительный просмотр FullScreenImage")
        FullScreenImage()
    }
}
