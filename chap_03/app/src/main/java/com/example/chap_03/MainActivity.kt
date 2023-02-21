package com.example.chap_03

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {


            BoxWithConstraints(

                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .drawYellowCross()
            ) {
                Column(
                    modifier = Modifier.width(min(400.dp, maxWidth)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val color = remember { mutableStateOf(Color.Magenta) }
                    ColorPicker(color)
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color.value),
                        text = "#${color.value.toArgb().toUInt().toString(16)}",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h4.merge(
                            TextStyle(
                                color = color.value.complementary()
                            )
                        )
                    )
                }
            }

            TextWithWarning1("", Modifier.background(Color.Blue)) {
            }
            TextWithWarning3(
                Modifier
                    .background(Color.Blue)
                    .padding(79.dp)) {
            }
        }
    }
}

@Composable
fun ColorPicker(color: MutableState<Color>) {
    val red = color.value.red
    val green = color.value.green
    val blue = color.value.blue
    Column {
        Slider(
            value = red,
            onValueChange = { color.value = Color(it, green, blue) }
        )
        Slider(
            value = green,
            onValueChange = { color.value = Color(red, it, blue) }
        )
        Slider(
            value = blue,
            onValueChange = { color.value = Color(red, green, it) }
        )
    }
}

fun Modifier.drawYellowCross() = then(
    object : DrawModifier {
        override fun ContentDrawScope.draw() {
            drawLine(
                color = Color.Yellow,
                start = Offset(0F, 0F),
                end = Offset(size.width - 1, size.height - 1),
                strokeWidth = 10F
            )
            drawLine(
                color = Color.Yellow,
                start = Offset(0F, size.height - 1),
                end = Offset(size.width - 1, 0F),
                strokeWidth = 10F
            )
            drawContent()
        }
    }
)

fun Color.complementary() = Color(
    red = 1F - red,
    green = 1F - green,
    blue = 1F - blue
)


@Composable
fun TextWithWarning1(

    name: String = "Default",
    modifier: Modifier = Modifier,

    callback: () -> Unit
) {
    Text(text = "TextWithWarning1 $name!", modifier = modifier
        .background(Color.Yellow)
        .clickable { callback.invoke() })
}

@Composable
fun TextWithWarning3(
    modifier: Modifier = Modifier,
    name: String = "Default",
    callback: () -> Unit
) {
    Column() {
        Text(
            text = "TextWithWarning1 $name!",
            modifier = modifier
                .background(Color.Yellow)
                .padding(12.dp)
                .clickable { callback.invoke() })

        Text(
            text = "TextWithWarning1 $name!",
            modifier = modifier
        )
    }
}


@Composable
fun TextWithWarning4(
    modifier: Modifier = Modifier,
    name: String,

    callback: () -> Unit
) {
    Text(text = "TextWithWarning1 $name!", modifier = modifier
        .background(Color.Yellow)
        .clickable { callback.invoke() })
}

@Composable
fun TextWithWarning2(test: Modifier = Modifier, name: String = "", callback: () -> Unit) {
    Text(text = "TextWithWarning2 $name!", modifier = test
        .background(Color.Yellow)
        .clickable { callback.invoke() })
}

@Composable
fun TextWithoutWarning(
    modifier: Modifier = Modifier,
    buttonModifier: Modifier,
    name: String = "",
    callback: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "TextWithoutWarning $name!", modifier = modifier
            .padding(10.dp) // margin concept
            .background(Color.Yellow)
            .padding(10.dp) // real padding
            .clickable { callback.invoke() })

        val context = LocalContext.current
        Button(
            modifier = buttonModifier.clickable {
                Toast.makeText(context, "버튼에 clickable을 넣으면?", Toast.LENGTH_SHORT).show()
            },
            onClick = { Toast.makeText(context, "버튼 클릭됨", Toast.LENGTH_SHORT).show() }) {
            Text("버튼")
        }
    }
}


