package ca.qc.happybirthday

import android.media.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.qc.happybirthday.ui.theme.HappyBirthdayTheme
import org.w3c.dom.Text

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HappyBirthdayTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MonAppli()
                }
            }
        }
    }
}

@Composable
fun TextAccueil(message: String, auteur: String, modifier: Modifier = Modifier){
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier.padding(8.dp)
        ) {
        Text(
            text = message,
            fontSize = 60.sp,
            lineHeight = 66.sp,
            textAlign = TextAlign.Center
        )
        Text(
            text = auteur,
            fontSize = 30.sp,
            modifier = Modifier
                .padding(16.dp)
                .align(alignment = Alignment.End)
        )
    }
}

@Composable
fun ImageAccueil(modifier: Modifier = Modifier) {
    val image = painterResource(R.drawable.background)
    Image(
        painter = image,
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Crop,
        alpha = 0.5F
    )
}

@Composable
fun MonAppli(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        ImageAccueil(modifier.fillMaxSize())
        TextAccueil("Happy birthday buddy!", "from todd", Modifier.fillMaxSize().padding(8.dp))
    }
}

@Preview(showBackground = true,
    showSystemUi = true,
    name = "B-Day app preview")
@Composable
fun GreetingPreview() {
    HappyBirthdayTheme {
        MonAppli()
    }
}