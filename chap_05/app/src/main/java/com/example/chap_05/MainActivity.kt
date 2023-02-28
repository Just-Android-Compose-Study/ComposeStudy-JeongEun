package com.example.chap_05

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
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
        }
    }
}

@Composable
@Preview
fun SimpleStateDemo1(){
    // num은 값 홀더의 참조 가짐.
    // num.value로 숫자를 얻고, 값 변경 가능
    val num =  remember { mutableStateOf(Random.nextInt(0, 10)) }
    Text(text = num.value.toString())

    // by 사용 -> 값을 저장(상태 자신을 num2에 할당x)
    // num2를 변경하려면 var
    val num2 by remember { mutableStateOf(Random.nextInt(0, 10)) }
    Text(text = num2.toString())
}

@Composable
@Preview
fun RememberObserver(){
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
fun SimpleStatelessComposable1(){
    Text(text = "Hello Compose")
}

@Composable
fun SimpleStatelessComposable2(text: State<String>){
    Text(text = text.value)
}

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

