package ca.qc.narutoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.qc.narutoapp.ui.theme.NarutoAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NarutoAppTheme {
                Scaffold(
                    topBar = { AppTopBar() }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        TableauPersonnage()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar() {
    TopAppBar(
        title = { Text("Naruto App") }
    )
}

@Composable
fun TableauPersonnage(modifier: Modifier = Modifier) {
    Box{
        Row {
            Column(
                Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(150.dp)) {
                Personnage(R.drawable.naruto, "Naruto", modifier.weight(1f));
                Personnage(R.drawable.sasuke, "Sasuke", modifier.weight(1f));
            }
            Column(
                Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(150.dp)) {
                Personnage(R.drawable.itachi, "Itachi", modifier.weight(1f));
                Personnage(R.drawable.kakashi, "Kakashi", modifier.weight(1f));
            }
        }
    }
}
@Composable
fun Personnage(idImagePersonnage: Int, nomPersonnage: String, modifier: Modifier = Modifier) {
    val image = painterResource(idImagePersonnage)
    Column(
        modifier = Modifier.padding(1.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )  {
        Image(
            painter = image,
            contentDescription = nomPersonnage,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .width(200.dp),
        )
        Text(
            modifier = Modifier.padding(1.dp),
            text="Je suis $nomPersonnage",
            fontSize = 25.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NarutoAppTheme {
        TableauPersonnage();
    }
}