package com.example.chap_01

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Hello()
        }
    }
}

@Composable
@Preview(group = "my-group-1", device = Devices.AUTOMOTIVE_1024p)
fun GreetingWrapper() { // 매개변수 있는 컴포저블 함수를 감싸서 미리보기 -> 구림
    Greeting("Jetpack Compose")
}

@Composable
@Preview(group = "my-group-1")
fun AltGreeting(name: String = "Jetpack Compose") { // 컴포저블 함수에 기본값 추가 - 기본값을 정의하지 않은 이유가 있는 경우 부적절
    Text(
        text = stringResource(id = R.string.hello, name),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineSmall
    )
}

class HelloProvider : PreviewParameterProvider<String> {
    override val values: Sequence<String>
        get() = listOf("PreviewParameterProvider").asSequence()
}

@Composable
@Preview
fun AltGreeting2(@PreviewParameter(HelloProvider::class)
                 name: String) {
    Text(
        text = stringResource(id = R.string.hello, name),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineSmall
    )
}

@Composable
@Preview
fun Welcome(){
    Text(text = stringResource(id = R.string.welcome))
}

@Composable
fun Greeting(name: String){
    Text(
        text = stringResource(id = R.string.hello, name),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineSmall
    )
}

@Composable
fun TextAndButton(name: MutableState<String>, nameEntered: MutableState<Boolean>) {
    Row(modifier = Modifier.padding(top = 8.dp)) {
        TextField(
            value = name.value,
            onValueChange = {
                name.value = it
            },
            placeholder = {
                Text(text = stringResource(id = R.string.hint))
            },
            modifier = Modifier
                .alignByBaseline()
                .weight(1.0F),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                capitalization = KeyboardCapitalization.Words,
            ),
            keyboardActions = KeyboardActions(onAny = {
                nameEntered.value = true
            })
        )
        Button(modifier = Modifier
            .alignByBaseline()
            .padding(8.dp),
            onClick = {
                nameEntered.value = true
            }) {
            Text(text = stringResource(id = R.string.done))
        }
    }
}

@Composable
fun Hello() {
    val name = remember { mutableStateOf("") }
    val nameEntered = remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (nameEntered.value) {
            Greeting(name.value)
        } else {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Welcome()
                TextAndButton(name, nameEntered)
            }
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    StudyChap1Theme {
//        Greeting("Android")
//    }
//}