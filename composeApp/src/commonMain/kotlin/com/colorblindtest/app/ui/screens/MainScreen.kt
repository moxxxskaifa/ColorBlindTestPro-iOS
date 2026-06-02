package com.colorblindtest.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

data class Question(val circles: List<Pair<Offset, Float>>, val answer: Int, val options: List<String>)

@Composable
fun MainScreen() {
    var currentQ by remember { mutableIntStateOf(0) }
    var score by remember { mutableIntStateOf(0) }
    var showResult by remember { mutableStateOf(false) }

    // Simplified Ishihara test simulation
    val questions = listOf(
        listOf("12", "14", "8", "21") to 0,
        listOf("5", "7", "2", "8") to 1,
        listOf("16", "73", "13", "26") to 2,
        listOf("42", "24", "57", "10") to 3,
        listOf("8", "6", "9", "3") to 1,
        listOf("29", "74", "15", "35") to 0,
        listOf("3", "7", "5", "2") to 2,
        listOf("10", "17", "28", "19") to 3,
    )

    Box(Modifier.fillMaxSize().background(Color(0xFFF5F5F5)), contentAlignment = Alignment.Center) {
        if (showResult) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(40.dp)) {
                Text("Results", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(16.dp))
                Text("{score}/{questions.size} correct", fontSize = 48.sp, fontWeight = FontWeight.Bold,
                     color = if (score >= 6) Color(0xFF27AE60) else Color(0xFFE74C3C))
                Spacer(Modifier.height(8.dp))
                Text(if (score >= 6) "Normal color vision detected" else "Possible color vision deficiency",
                     fontSize = 16.sp)
                Spacer(Modifier.height(24.dp))
                Button(onClick = { currentQ = 0; score = 0; showResult = false }) { Text("Retake Test") }
            }
        } else {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Test {currentQ + 1}/{questions.size}", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                Spacer(Modifier.height(16.dp))
                // Simulated color plate
                Box(Modifier.size(200.dp).clip(CircleShape).background(Color(0xFF2ECC71)), contentAlignment = Alignment.Center) {
                    Text(questions[currentQ].first[questions[currentQ].second],
                         fontSize = 48.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE74C3C))
                }
                Spacer(Modifier.height(20.dp))
                Text("What number do you see?", fontSize = 14.sp)
                Spacer(Modifier.height(12.dp))
                questions[currentQ].first.forEachIndexed { idx, opt ->
                    Button(onClick = {
                        if (idx == questions[currentQ].second) score++
                        if (currentQ < questions.size - 1) currentQ++ else showResult = true
                    }, modifier = Modifier.fillMaxWidth().padding(horizontal = 40.dp, vertical = 4.dp),
                       shape = RoundedCornerShape(12.dp),
                       colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF34495E))) {
                        Text(opt, fontSize = 18.sp)
                    }
                }
            }
        }
    }
}
