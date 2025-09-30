package ca.qc.dicegame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ca.qc.dicegame.ui.theme.DiceGameTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DiceGameTheme {
                DiceRollerApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DiceRollerApp() {
    DiceWithButtonAndImage(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center))
}

@Composable
fun DiceWithButtonAndImage(modifier: Modifier = Modifier) {
    var result by remember { mutableStateOf(1) }
    var currentPlayerOne by remember { mutableStateOf(true) }
    var scorePlayerOne by remember { mutableStateOf(0) }
    var scorePlayerTwo by remember { mutableStateOf(0) }
    var isGameOver by remember { mutableStateOf(false) }
    var winner by remember { mutableStateOf(0) }
    var turnPoint by remember { mutableStateOf(0) }

    val imageResource = when (result) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(imageResource),
            contentDescription = result.toString()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (isGameOver) "Winner: Player $winner"
            else "Turn: Player ${if (currentPlayerOne) 1 else 2}"
        )
        Text("Turn points: $turnPoint")
        Spacer(modifier = Modifier.height(8.dp))

        // Roll + Keep
        Row {
            Button(
                enabled = !isGameOver,
                onClick = {
                    result = (1..6).random()
                    if (result == 1) {
                        turnPoint = 0
                        currentPlayerOne = !currentPlayerOne
                    } else {
                        turnPoint += result
                    }
                }
            ) { Text(stringResource(R.string.roll)) }

            Spacer(Modifier.padding(8.dp))

            Button(
                enabled = !isGameOver,
                onClick = {
                    if (currentPlayerOne) {
                        scorePlayerOne += turnPoint
                        if (scorePlayerOne >= 25) {
                            isGameOver = true
                            winner = 1
                        }
                    } else {
                        scorePlayerTwo += turnPoint
                        if (scorePlayerTwo >= 25) {
                            isGameOver = true
                            winner = 2
                        }
                    }
                    turnPoint = 0
                    if (!isGameOver) currentPlayerOne = !currentPlayerOne
                }
            ) { Text("Keep") }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Score player One: $scorePlayerOne")
        Text("Score player Two: $scorePlayerTwo")

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 New Game button
        Button(
            onClick = {
                result = 1
                currentPlayerOne = true
                scorePlayerOne = 0
                scorePlayerTwo = 0
                isGameOver = false
                winner = 0
                turnPoint = 0
            }
        ) { Text("New Game") }
    }
}


