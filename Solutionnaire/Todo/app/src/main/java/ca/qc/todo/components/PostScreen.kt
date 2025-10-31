import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ca.qc.todo.network.Post
import ca.qc.todo.network.TodoViewModel

@Composable
fun PostScreen() {
    val viewModel: TodoViewModel = viewModel()
    val posts by viewModel.posts.collectAsState()
    var userId by remember { mutableStateOf("0") }

    Column(Modifier.padding(16.dp)) {
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            label = { Text("User ID") },
            value = userId,
            onValueChange = { userId = it }
        )

        Button(onClick = { viewModel.loadPosts(userId.toInt()) }) {
            Text("Fetch Posts")
        }

        LazyColumn {
            items(posts) { post: Post ->
                Text("${post.title} — ${post.body}")
                HorizontalDivider(
                    thickness = DividerDefaults.Thickness,
                    color = DividerDefaults.color
                )
            }
        }
    }
}
