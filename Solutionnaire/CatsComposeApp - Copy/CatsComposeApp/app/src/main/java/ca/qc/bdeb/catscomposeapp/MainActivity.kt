package ca.qc.bdeb.catscomposeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import ca.qc.bdeb.catscomposeapp.ui.theme.CatsComposeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CatsComposeAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MonAppli(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MonAppli(modifier: Modifier = Modifier) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ){
        ImageAccueil()
    }
}

@Composable
fun ImageAccueil(){
    Row(modifier = Modifier.fillMaxHeight())
    {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                val image1 = painterResource((R.drawable.ss1))
                Image(
                    painter = image1,
                    contentDescription = null
                )
                Text(
                    "Chat 1",
                    textAlign = TextAlign.Left,
                    fontSize = 25.sp
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                val image2 = painterResource((R.drawable.ss2))
                Image(
                    painter = image2,
                    contentDescription = null
                )
                Text(
                    "Chat 2",
                    textAlign = TextAlign.Justify,
                    fontSize = 25.sp
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                val image3 = painterResource((R.drawable.ss3))
                Image(
                    painter = image3,
                    contentDescription = null
                )
                Text(
                    "Chat 3",
                    textAlign = TextAlign.Left,
                    fontSize = 25.sp
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                val image4 = painterResource((R.drawable.ss4))
                Image(
                    painter = image4,
                    contentDescription = null
                )
                Text(
                    "Chat 4",
                    textAlign = TextAlign.Justify,
                    fontSize = 25.sp
                )
            }
        }
    }


}

@Preview(showBackground = true)

@Composable
fun GreetingPreview() {
    CatsComposeAppTheme {
        MonAppli()
    }
}