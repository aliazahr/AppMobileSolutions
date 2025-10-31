package ca.qc.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ca.qc.todo.network.Post
import ca.qc.todo.network.Todo
import ca.qc.todo.network.TodoViewModel
import ca.qc.todo.ui.theme.TodoTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import ca.qc.todo.components.EmptyScreen
import ca.qc.todo.components.TodoDetail
import ca.qc.todo.components.TodoScreen


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)
            TodoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TodoAppScreen(windowSizeClass, modifier = androidx.compose.ui.Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun TodoAppScreen(sizeClass: WindowSizeClass, modifier: Modifier = Modifier) {
    val width = sizeClass.widthSizeClass
    val viewModel: TodoViewModel = viewModel()
    val todos by viewModel.todos.collectAsState()

    var selected by remember { mutableStateOf<Int?>(null) }

    when (width) {
        WindowWidthSizeClass.Expanded, WindowWidthSizeClass.Medium -> Row(modifier = modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .weight(0.4f)
                    .fillMaxHeight()
            ) {
                TodoScreen(onTodoClick = { selected = it })
            }
            Box(
                modifier = Modifier
                    .weight(0.6f)
                    .fillMaxHeight()
            ) {
                if (selected != null) {
                    TodoDetail(todos[selected!!])
                } else {
                    EmptyScreen()
                }
            }
        }

        else -> if (selected == null) {
            TodoScreen(onTodoClick = { selected = it })
        } else {
            TodoDetail(todos[selected!!])
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TodoTheme {
        Greeting("Android")
    }
}