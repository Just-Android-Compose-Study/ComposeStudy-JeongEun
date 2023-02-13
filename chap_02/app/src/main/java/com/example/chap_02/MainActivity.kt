package com.example.chap_02

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chap_02.ui.theme.Chap_02Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Factorial()
        }
    }
}

@Composable
@Preview
fun Factorial() {
    var expanded by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf(factorialAsString(0)) }
    Box(
        modifier = Modifier.fillMaxSize(), // 박스 사이즈
        contentAlignment = Alignment.Center // content 정렬
    ) {
        Text(
            modifier = Modifier.clickable {
                expanded = true
            },
            text = text,
            style = MaterialTheme.typography.h2
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { // 아무것도 선택하지 않고 닫음
                expanded = false
            }) {
            for (n in 0 until 10){
                DropdownMenuItem(onClick = {
                    expanded = false
                    text = factorialAsString(n)
                }) {
                    Text("${n.toString()}!")
                }
            }
        }
    }
}

fun factorialAsString(n: Int): String {
    var result = 1L
    for (i in 1..n) {
        result *= i
    }
    return "$n! = $result"
}