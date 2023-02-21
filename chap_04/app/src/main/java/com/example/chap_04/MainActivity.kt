package com.example.chap_04

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //PredefinedLayoutDemo()
            //ConstraintLayoutDemo()
            ConstraintLayoutDemo()
            //CustomLayoutDemo()

        }
    }
}

// TODO : 실습 1
@Composable
fun CheckboxWithLabel(label: String, state: MutableState<Boolean>) {
    Row(
        modifier = Modifier.clickable {
            state.value = !state.value // ?
        }, verticalAlignment = Alignment.CenterVertically //수직선에서 가운데에 위치시킴
    ) {
        Checkbox(
            checked = state.value, //현재 상태
            onCheckedChange = { //체크박스를 선택하면 호출
                state.value = it
            })
        Text(
            text = label,
            modifier = Modifier
                .padding(start = 8.dp)
        )
    }
}

@Composable
@Preview
fun PredefinedLayoutDemo() {
    val red = remember { mutableStateOf(true) }
    val green = remember { mutableStateOf(true) }
    val blue = remember { mutableStateOf(true) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        CheckboxWithLabel(
            label = stringResource(R.string.red),
            state = red
        )
        CheckboxWithLabel(
            label = stringResource(R.string.blue),
            state = blue
        )
        CheckboxWithLabel(
            label = stringResource(R.string.green),
            state = green
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
        ) {
            if(red.value) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Red)
                )
            }
            if(blue.value){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp)
                        .background(Color.Blue)
                )
            }
            if(green.value){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(64.dp)
                        .background(Color.Green)
                )
            }
        }
    }
}

// TODO : 실습 2
@Composable
fun CheckboxWithLabel(
    label: String,
    state: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(modifier = Modifier.clickable {
        state.value = !state.value
    }) {
        val (checkbox, text) = createRefs()
        Checkbox(
            checked = state.value,
            onCheckedChange = {
                state.value = it
            },
            modifier = Modifier.constrainAs(checkbox) {
            }
        )
        Text(
            text = label,
            modifier = Modifier.constrainAs(text) {
                start.linkTo(checkbox.end, margin = 8.dp)
                top.linkTo(checkbox.top)
                bottom.linkTo(checkbox.bottom)
            })
    }
}

@Composable
fun ConstraintLayoutDemo() {
    val red = remember { mutableStateOf(true) }
    val blue = remember { mutableStateOf(true) }
    val green = remember { mutableStateOf(true) }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val (cbRed, cbGreen, cbBlue, boxRed, boxGreen, boxBlue) = createRefs()
        CheckboxWithLabel(
            label = stringResource(id = R.string.red),
            state = red,
            modifier = Modifier.constrainAs(cbRed) {
                top.linkTo(parent.top)
            })
        CheckboxWithLabel(
            label = stringResource(id = R.string.blue),
            state = blue,
            modifier = Modifier.constrainAs(cbBlue) {
                top.linkTo(cbRed.bottom)
            }
        )
        CheckboxWithLabel(
            label = stringResource(id = R.string.green),
            state = green,
            modifier = Modifier.constrainAs(cbGreen) {
                top.linkTo(cbBlue.bottom)
            }
        )

        if (red.value) {
            Box(
                modifier = Modifier
                    .background(Color.Red)
                    .constrainAs(boxRed) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(cbBlue.bottom, margin = 16.dp)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
            )
        }

        if (green.value) {
            Box(
                modifier = Modifier
                    .background(Color.Green)
                    .constrainAs(boxGreen) {
                        start.linkTo(parent.start, margin = 32.dp)
                        end.linkTo(parent.end, margin = 32.dp)
                        top.linkTo(
                            cbBlue.bottom,
                            margin = (16 + 32).dp
                        )// RedBox는 CheckBox에 따라 생성되지 않을 수도 있으므로 제약조건을 걸기에 부적합
                        bottom.linkTo(parent.bottom, margin = 32.dp)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
            )
        }

        if (blue.value) {
            Box(
                modifier = Modifier
                    .background(Color.Green)
                    .constrainAs(boxGreen) {
                        start.linkTo(parent.start, margin = 64.dp)
                        end.linkTo(parent.end, margin = 64.dp)
                        top.linkTo(
                            cbBlue.bottom,
                            margin = (16 + 64).dp
                        )// RedBox는 CheckBox에 따라 생성되지 않을 수도 있으므로 제약조건을 걸기에 부적합
                        bottom.linkTo(parent.bottom, margin = 64.dp)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
            )
        }
    }
}

// TODO : 실습3
@Composable
@Preview
fun ColumnWithText(){
    Column {
        Text(
            text = "Android UI development with Jetpack Compose",
            style = MaterialTheme.typography.h3
        )
        Text(
            text = "Hello Compose",
            style = MaterialTheme.typography.h5.merge(TextStyle(color = Color.Red))
        )

    }
}


//TODO : 실습4
@Composable
fun ColoredBox() {
    Box(
        modifier = Modifier
            .border(
                width = 2.dp,
                color = Color.Black
            )
            .background(randomColor())
            .width((40 * randomInt123()).dp)
            .height((10 * randomInt123()).dp)
    )

}

private fun randomInt123() = Random.nextInt(1, 4)

private fun randomColor() = when(randomInt123()){
    1 -> Color.Red
    2 -> Color.Green
    else -> Color.Blue
}

@Composable
@Preview
fun CustomLayoutDemo(){
    SimpleFlexBox {
        for (i in 0..42){
            ColoredBox()
        }
    }
}

@Composable
fun SimpleFlexBox(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
){
    Layout(
        modifier = modifier,
        content = content,
        measurePolicy = simpleFlexboxMeasurePolicy()
    )
}

private fun simpleFlexboxMeasurePolicy(): MeasurePolicy = MeasurePolicy {measureables, constraints ->
    val placeables = measureables.map {measureable ->
        measureable.measure(constraints)
    }
    layout(
        constraints.maxWidth,
        constraints.maxHeight
    ){
        var yPos = 0
        var xPos = 0
        var maxY = 0
        placeables.forEach{placeable ->
            if(xPos + placeable.width >
                    constraints.maxWidth
            ){
                xPos = 0
                yPos += maxY
                maxY = 0
            }
            placeable.placeRelative(
                x = xPos,
                y = yPos
            )
            xPos += placeable.width
            if (maxY < placeable.height){
                maxY = placeable.height
            }
        }
    }
}
