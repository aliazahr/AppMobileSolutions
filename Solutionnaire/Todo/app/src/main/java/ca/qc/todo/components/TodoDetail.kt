package ca.qc.todo.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ca.qc.todo.network.Todo

@Composable
fun TodoDetail(todo: Todo) {
    Column {
        Text(todo.titre ?: "Untitled", style = androidx.compose.material3.MaterialTheme.typography.headlineLarge)
        Text(todo.complete.toString(), style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)
    }
}
