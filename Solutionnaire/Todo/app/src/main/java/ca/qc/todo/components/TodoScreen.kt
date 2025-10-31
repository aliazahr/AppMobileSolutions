package ca.qc.todo.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import ca.qc.todo.network.TodoViewModel

@Composable
fun TodoScreen(onTodoClick: (Int) -> Unit) {
    val viewModel: TodoViewModel = viewModel()
    val todos by viewModel.todos.collectAsState()

    Column {
        Button(onClick = { viewModel.loadTodos() }) {
            Text("Fetch Todos")
        }

        LazyColumn {
            items(items = todos) { todo ->
                Column(modifier = Modifier.clickable { onTodoClick(todos.indexOf(todo)) }) {
                    Text(text = "${todo.titre} (${if (todo.complete == true) "done" else "pending"})")
                    HorizontalDivider(
                        thickness = DividerDefaults.Thickness,
                        color = DividerDefaults.color
                    )
                }
            }
        }
    }
}