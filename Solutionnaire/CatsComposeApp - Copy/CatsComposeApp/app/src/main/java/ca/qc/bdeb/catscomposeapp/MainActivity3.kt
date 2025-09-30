package ca.qc.bdeb.catscomposeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.qc.bdeb.catscomposeapp.ui.theme.CatsComposeAppTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class MainActivity3 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CatsComposeAppTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    DiceRollerApp()
//                }
                DiceRollerApp()
            }
        }
    }
}

//@Composable
//fun MonAppli3(modifier: Modifier = Modifier){
//    Surface(
//        modifier = Modifier.fillMaxSize(),
//        color = MaterialTheme.colorScheme.background
//    ) {
//
//    }
//}

@Preview
@Composable
fun DiceRollerApp(){
    DiceWithButtonAndImage(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center))
}

@Composable
fun DiceWithButtonAndImage(modifier: Modifier = Modifier){
    // result of the dice
    var result by remember { mutableStateOf(1) }

    // points in the round
    var turnPoints by remember { mutableStateOf(0) }

    // player 1 points
    var totalP1 by remember { mutableStateOf(0) }

    // player 2 points
    var totalP2 by remember { mutableStateOf(0) }

    // whos turn it is
    var isPlayer1Turn by remember { mutableStateOf(true) }

    // winner
    var winner: Int? by remember { mutableStateOf(null) }

    var currentPlayer =
        if (isPlayer1Turn)
            "Joueur 1"
        else
            "Joueur 2"


    val imageResource = when (result) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }

    // if the turn points is 0, it means they got 1 --> switch turn
    fun endTurn(switch: Boolean = true) {
        turnPoints = 0
        if (switch)
            isPlayer1Turn = !isPlayer1Turn
    }

    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("----- Objectif: 25 points -----")

        Spacer(Modifier.height(8.dp))

        // Points of each player
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Joueur 1: $totalP1")
            Text("Joueur 1: $totalP2")
        }

        Spacer(Modifier.height(10.dp))

        // Current player + the points in the turn
        Text("Tour de: $currentPlayer")
        Text("Points du tour: $turnPoints")

        Spacer(Modifier.height(10.dp))


        Text("Resultat du dice: $result")

        Image(
            painter = painterResource(imageResource),
            contentDescription = result.toString()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // ==== ROLL ====
                Button(
                    enabled =
                        winner == null,

                    onClick = {
                        result = (1..6).random()

                        // if its 1 --> switch turn
                        if (result == 1) {
                            endTurn(switch = true)
                        }
                        else {
                            turnPoints += result
                        }
                    }
                ) {
                    Text(stringResource(R.string.roll))
                }

                Spacer(modifier = Modifier.width(10.dp))

                // ==== GARDER ====
                Button(
                    // enabled if no winner and if points is 0
                    enabled = winner == null && turnPoints > 0,

                    onClick = {
                        if (isPlayer1Turn) {
                            totalP1 += turnPoints
                            if (totalP1 >= 25) {
                                winner = 1
                            }
                        } else {
                            totalP2 += turnPoints
                            if (totalP2 >= 25) {
                                winner = 2
                            }
                        }

                        endTurn(switch = winner == null)
                    }
                ) {
                    Text(stringResource(R.string.garder))
                }

                Spacer(modifier = Modifier.width(10.dp))

                // ==== NEW ====
                Button(
                    onClick = {
                        result = 1
                        turnPoints = 0
                        totalP1 = 0
                        totalP2 = 0
                        isPlayer1Turn = true
                        winner = null
                    }
                ) {
                    Text(stringResource(R.string.nouveau))
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Text results
            when {
                winner != null -> {
                    Text("${if (winner == 1) "Joueur 1" else "Joueur 2"} a gangné !!!! :DDD")
                }
                result == 1 && turnPoints == 0 -> {
                    Text("Oh non!! 1 lancé: points du tour perdus. Tour suivant !")
                }
                else -> {
                    Text("Rouleur encore ou garder ?")
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    CatsComposeAppTheme {
        DiceRollerApp()
    }
}