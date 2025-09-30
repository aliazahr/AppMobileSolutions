package ca.qc.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.qc.myapplication.ui.theme.MyApplicationTheme

// --- DATA CLASS ---
data class TvShow(
    val id: Int,
    var title: String,
    var year: Int,
    var network: String,
    var isFavorite: Boolean
)

val sampleShows = listOf(
    TvShow(1, "Breaking Bad", 2008, "AMC", true),
    TvShow(2, "Better Call Saul", 2015, "AMC", false),
    TvShow(3, "Game of Thrones", 2011, "HBO", false),
    TvShow(4, "The Sopranos", 1999, "HBO", true),
    TvShow(5, "The Wire", 2002, "HBO", false),
    TvShow(6, "Stranger Things", 2016, "Netflix", false),
    TvShow(7, "Succession", 2018, "HBO", true),
    TvShow(8, "Mad Men", 2007, "AMC", false),
    TvShow(9, "Dark", 2017, "Netflix", false),
    TvShow(10, "Mindhunter", 2017, "Netflix", true),
    TvShow(11, "The Crown", 2016, "Netflix", false),
    TvShow(12, "True Detective", 2014, "HBO", false)
)

// --- SCREEN ENUM ---
enum class Screen { LIST, EDIT }

// --- MAIN ACTIVITY ---
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Application()
            }
        }
    }
}

// --- ROOT COMPOSABLE ---
@Composable
fun Application() {
    var screen by remember { mutableStateOf(Screen.LIST) }
    var selectedId by remember { mutableIntStateOf(0) }
    val shows = remember { mutableStateListOf<TvShow>().apply { addAll(sampleShows) } }

    when (screen) {
        Screen.LIST -> ShowsList(
            shows = shows,
            onShowClick = { id ->
                selectedId = id
                screen = Screen.EDIT
            }
        )

        Screen.EDIT -> EditShowScreen(
            show = shows.first { it.id == selectedId },
            onSave = { updated ->
                val index = shows.indexOfFirst { it.id == updated.id }
                if (index != -1) shows[index] = updated
                screen = Screen.LIST
            },
            onCancel = {
                screen = Screen.LIST
            }
        )
    }
}

// --- LIST SCREEN ---
@Composable
fun ShowsList(
    shows: List<TvShow>,
    onShowClick: (Int) -> Unit
) {
    LazyColumn {
        items(items = shows) { show ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable { onShowClick(show.id) },
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(show.title, style = MaterialTheme.typography.titleMedium)
                    Text("${show.network} • ${show.year}", style = MaterialTheme.typography.bodyMedium)
                }
                if (show.isFavorite) Text("★", style = MaterialTheme.typography.titleLarge)
            }
            HorizontalDivider()
        }
    }
}

// --- EDIT SCREEN ---
@Composable
fun EditShowScreen(
    show: TvShow,
    onSave: (TvShow) -> Unit,
    onCancel: () -> Unit
) {
    var title by remember { mutableStateOf(show.title) }
    var year by remember { mutableStateOf(show.year.toString()) }
    var network by remember { mutableStateOf(show.network) }
    var isFav by remember { mutableStateOf(show.isFavorite) }

    Column(Modifier.padding(16.dp)) {
        OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = year, onValueChange = { year = it }, label = { Text("Year") })
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = network, onValueChange = { network = it }, label = { Text("Network") })
        Spacer(Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = isFav, onCheckedChange = { isFav = it })
            Text("Favorite")
        }
        Spacer(Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = {
                onSave(show.copy(title = title, year = year.toIntOrNull() ?: show.year, network = network, isFavorite = isFav))
            }) {
                Text("Save")
            }
            OutlinedButton(onClick = onCancel) {
                Text("Cancel")
            }
        }
    }
}

// --- PREVIEW ---
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Application()
    }
}
