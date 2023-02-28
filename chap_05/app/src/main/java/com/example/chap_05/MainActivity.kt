package com.example.chap_05

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chap_05.ui.theme.Chap_05Theme
import java.util.*
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            SimpleStateDemo1()
//            RememberObserver()
//            SimpleStatelessComposable1()
//            FlowOfEventsDemo()
            RememberSaveable()
        }
    }
}

@Composable
@Preview
fun SimpleStateDemo1() {
    // num은 값 홀더의 참조 가짐.
    // num.value로 숫자를 얻고, 값 변경 가능
    val num = remember { mutableStateOf(Random.nextInt(0, 10)) }
    Text(text = num.value.toString())

    // by 사용 -> 값을 저장(상태 자신을 num2에 할당x)
    // num2를 변경하려면 var
    val num2 by remember { mutableStateOf(Random.nextInt(0, 10)) }
    Text(text = num2.toString())
}

@Composable
@Preview
fun RememberObserver() {
    var key by remember { mutableStateOf(false) }
    val date by remember(key) { mutableStateOf(Date()) }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(date.toString())
        Button(onClick = { key != key }) {
            Text(text = "click")
        }
    }
}

@Composable
fun SimpleStatelessComposable1() {
    Text(text = "Hello Compose")
}

@Composable
fun SimpleStatelessComposable2(text: State<String>) {
    Text(text = text.value)
}


// TODO : 섭씨 화씨 변환 예제
// stateless
@Composable
fun TextFieldDemo(state: MutableState<TextFieldValue>) {
    TextField(
        value = state.value,
        onValueChange = { state.value = it },
        placeholder = { Text("Hello") },
        modifier = Modifier.fillMaxWidth()
    )
}

// stateful
@Composable
@Preview
fun TextFieldDemo() {
    val state = remember {
        mutableStateOf(TextFieldValue(""))
    }
    TextFieldDemo(state)
}

@Composable
fun TemperatureTextField(
    temperature: MutableState<String>,  // 파라미터로 상태를 전달 받으며
    modifier: Modifier = Modifier,
    callback: () -> Unit
) {
    TextField(
        value = temperature.value,
        onValueChange = {
            temperature.value = it  // 변경 사항을 상태에 다시 저장한다.
        },
        placeholder = {
            Text(text = "온도")
        },
        modifier = modifier,
        keyboardActions = KeyboardActions(onAny = {
            callback()
        }),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        singleLine = true
    )
}

@Composable
fun TemperatureRadioButton(
    selected: Boolean,
    resId: Int,
    onClick: (Int) -> Unit, // 파라미터를 받는 콜백함수를 파라미터로 설정
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        RadioButton(
            selected = selected,
            onClick = {
                onClick(resId)  // onClick 콜백함수 호출
            }
        )
        Text(
            text = stringResource(resId),
            modifier = Modifier
                .padding(start = 8.dp)
        )
    }
}

@Composable
fun TemperatureScaleButtonGroup(
    selected: MutableState<Int>,    // 상태를 받음
    modifier: Modifier = Modifier
) {
    val sel = selected.value
    val onClick = { resId: Int -> selected.value = resId }  // 3: 새로운 상태 값으로 지정한다.
    Row(modifier = modifier) {
        TemperatureRadioButton(
            selected = sel == R.string.celsius,
            resId = R.string.celsius,   // 2: redId 값을
            onClick = onClick   // 1: 라디오 버튼을 클릭하면, (버블업, bubble up)
        )
        TemperatureRadioButton(
            selected = sel == R.string.fahrenheit,
            resId = R.string.fahrenheit,
            onClick = onClick,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

// Convert

@Composable
@Preview
fun FlowOfEventsDemo() {
    val strCelsius = stringResource(id = R.string.celsius)
    val strFahrenheit = stringResource(id = R.string.fahrenheit)
    val temperature = remember { mutableStateOf("") }
    val scale = remember { mutableStateOf(R.string.celsius) }
    var convertedTemperature by remember { mutableStateOf(Float.NaN) }
    val calc = {
        val temp = temperature.value.toFloat()
        convertedTemperature = if (scale.value == R.string.celsius)
            (temp * 1.8F) + 32F
        else
            (temp - 32F) / 1.8F
    }
    // 섭씨 화씨 전환
    val result = remember(convertedTemperature) {
        if (convertedTemperature.isNaN())
            ""
        else
            "${convertedTemperature}${
                if (scale.value == R.string.celsius)
                    strFahrenheit
                else strCelsius
            }"
    }
    val enabled = temperature.value.isNotBlank()    //  비어 있지 않으면 모두 활성화
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TemperatureTextField(
            temperature = temperature,
            modifier = Modifier.padding(bottom = 16.dp),
            callback = calc     // 키보드 액션에서 완료 눌렀을 때
        )
        TemperatureScaleButtonGroup(
            selected = scale,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Button(
            onClick = calc,     // 직접 변환
            enabled = enabled
        ) {
            Text(text = stringResource(id = R.string.convert))
        }
        if (result.isNotEmpty()) {  // 결과가 있을 때만 보이는 Text
            Text(
                text = result,
                style = MaterialTheme.typography.body1
            )
        }
    }
}


// TODO : rememberSaveable
@Composable
@Preview
fun RememberSaveable() {
    val state1 = remember {
        mutableStateOf("Hello #1")
    }
    val state2 = rememberSaveable {
        mutableStateOf("Hello #2")
    }
}
