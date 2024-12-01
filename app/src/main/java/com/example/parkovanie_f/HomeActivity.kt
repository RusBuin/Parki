package com.example.parkovanie_f

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.example.parkovanie_f.ui.theme.Parkovanie_fTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("HomeActivity", "onCreate called")  // Логируем запуск активности
        setContent {
            Log.d("HomeActivity", "Setting content")  // Логируем настройку контента
            Parkovanie_fTheme {
                HomeScreen()
            }
        }
    }
}

@Composable
fun HomeScreen() {
    var selectedItem by remember { mutableStateOf(0) }

    val items = listOf("Auto", "Search", "Settings")
    val icons = listOf(
        Icons.Default.Face,
        Icons.Default.Search,
        Icons.Default.Settings
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = Color.DarkGray,
                contentColor = Color.Transparent
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth().background(Color.DarkGray)
                ) {
                    items.forEachIndexed { index, item ->
                        val scale by animateFloatAsState(
                            targetValue = if (selectedItem == index) 1.2f else 1f,
                            animationSpec = tween(durationMillis = 300)
                        )

                        NavigationBarItem(
                            selected = selectedItem == index,
                            onClick = {
                                Log.d("HomeScreen", "Selected item: $item") // Log when an item is selected
                                selectedItem = index
                            },
                            icon = {
                                Box(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .background(
                                            color = if (selectedItem == index) Color(0xFF6200EE) else Color.Black,
                                            shape = CircleShape
                                        )
                                        .padding(10.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = icons[index],
                                        contentDescription = item,
                                        tint = if (selectedItem == index) Color.White else Color.LightGray,
                                        modifier = Modifier.scale(scale)
                                    )
                                }
                            },
                            label = { Text(item, color = if (selectedItem == index) Color.White else Color.Gray) },
                            alwaysShowLabel = false,
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Color.Transparent
                            )
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.DarkGray)
        ) {
            when (selectedItem) {
                0 -> {
                    Log.d("HomeScreen", "Navigating to Auto Screen") // Log navigation
                    TimeSelectionScreen()
                }
                1 -> {
                    Log.d("HomeScreen", "Navigating to Search Screen") // Log navigation
                    SearchScreen()
                }
                2 -> {
                    Log.d("HomeScreen", "Navigating to Settings Screen") // Log navigation
                    SettingsScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.padding(top = 50.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Text(
                    text = "Find Your",
                    style = androidx.compose.ui.text.TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp,
                        color = Color.White
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "",
                    style = androidx.compose.ui.text.TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp,
                        color = Color.White
                    )
                )
            }

            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Parking Space",
                    style = androidx.compose.ui.text.TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp,
                        color = Color.White
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "",
                    style = androidx.compose.ui.text.TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp,
                        color = Color.White
                    )
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.Black)
                    .clip(RoundedCornerShape(40.dp))
            ) {
                TextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text(text = "Search", style = androidx.compose.ui.text.TextStyle(color = Color.White)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 56.dp)
                        .border(2.dp, Color.Black, shape = RoundedCornerShape(24.dp)),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color(0xFF6200EE),
                        unfocusedIndicatorColor = Color.Gray
                    ),
                    shape = RoundedCornerShape(24.dp),
                    singleLine = true
                )

                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.CenterEnd)
                        .background(Color(0xFF6200EE), CircleShape)
                        .padding(8.dp),
                    tint = Color.White
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                painter = painterResource(id = R.drawable.map_road),
                contentDescription = "Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1400.dp)
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Your Car",
                        style = androidx.compose.ui.text.TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 38.sp,
                            color = Color.White
                        )
                    )

                    Text(
                        text = "ECV: A123BC",
                        style = androidx.compose.ui.text.TextStyle(
                            fontSize = 26.sp,
                            color = Color.White
                        )
                    )
                    Text(
                        text = "Type: Sedan",
                        style = androidx.compose.ui.text.TextStyle(
                            fontSize = 26.sp,
                            color = Color.White
                        )
                    )

                    Button(
                        onClick = {
                            Log.d("SearchScreen", "Edit button clicked") // Log when the Edit button is clicked
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)),
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text(
                            text = "Edit",
                            style = androidx.compose.ui.text.TextStyle(
                                fontSize = 18.sp,
                                color = Color.White
                            )
                        )
                    }
                }

                Image(
                    painter = painterResource(id = R.drawable.car),
                    contentDescription = "Car",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
            }
        }
    }
}

@Composable
fun SettingsScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Settings Screen", style = MaterialTheme.typography.titleLarge)
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    Parkovanie_fTheme {
        HomeScreen()
    }
}
