package ca.qc.bdeb.catscomposeapp

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ca.qc.bdeb.catscomposeapp.ui.theme.CatsComposeAppTheme

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CatsComposeAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MonAppli2(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MonAppli2(modifier: Modifier = Modifier) {
    Surface(
        color = MaterialTheme.colorScheme.background
    ){
        //BouttonSimple()
        //CompteurClicks()
        ChangeEtat()
    }
}

// EXERCICE 1
@Composable
fun BouttonSimple() {
    val TAG = "MY_APP"
    Button(onClick = {
        Log.i(TAG,"Bonjour!")
    })
    {
        Text("Click")
    }
}

// EXERCICE 2
@Composable
fun CompteurClicks() {
    var nbClicks by remember { mutableStateOf(0) }

    Column {
        Text("Nombre de clicks: $nbClicks")
        Button(onClick = {
            nbClicks++
        })
        {
            Text("Click me to count ur clicks :3")
        }
    }
}

// EXERCICE 3
@Composable
fun ChangeEtat() {
    var nbClicks by remember { mutableStateOf(0) }

    Column {
        Text("Nombre de clicks: $nbClicks")
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = {
                nbClicks++
            })
            {
                Text("Click me to add a click :3")
            }

            Button(onClick = {
                nbClicks = 0
            })
            {
                Text("Click me to reset clicks D:")
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    CatsComposeAppTheme {
        MonAppli2()
    }
}