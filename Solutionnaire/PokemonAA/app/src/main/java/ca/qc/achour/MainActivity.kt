package ca.qc.achour

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.qc.achour.ui.theme.PokemonAATheme

data class Ennemi(val vie: Int, val force: Int, val ennemiRessource: Int)

private val listeEnnemi: List<Ennemi> = listOf(
    Ennemi(20, 10, R.drawable.caterpie),
    Ennemi(50, 15, R.drawable.pikachu),
    Ennemi(100, 20, R.drawable.gyarados),
    Ennemi(1000, 1000, R.drawable.magikarp)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { PokemonAATheme { MonAppli() } }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonAppli(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    // ---- STATE (hissé) ----
    var playerLife by remember { mutableIntStateOf(100) }
    var playerForce by remember { mutableIntStateOf(10) }
    var enemyIndex by remember { mutableIntStateOf(0) }
    val enemyLives = remember { mutableStateListOf<Int>().apply { addAll(listeEnnemi.map { it.vie }) } }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("PokemonAA") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    // +1 Force
                    IconButton(onClick = {
                        playerForce += 1
                        Toast.makeText(context, "+1 force", Toast.LENGTH_SHORT).show()
                    }) { Icon(Icons.Default.Add, contentDescription = "Augmenter la force") }

                    // Reset partie
                    IconButton(onClick = {
                        playerLife = 100
                        playerForce = 10
                        enemyIndex = 0
                        enemyLives.clear()
                        enemyLives.addAll(listeEnnemi.map { it.vie })
                        Toast.makeText(context, "Partie réinitialisée", Toast.LENGTH_SHORT).show()
                    }) { Icon(Icons.Default.Delete, contentDescription = "Réinitialiser la partie") }
                }
            )
        }
    ) { padding ->
        // Disposition demandée : haut / centre / bas
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 1) HAUT : infos joueur
            HeaderSection(
                vie = playerLife,
                force = playerForce,
                modifier = Modifier.fillMaxWidth()
            )

            // 2) CENTRE : image Pokémon (grosse et uniforme)
            val enemy = listeEnnemi[enemyIndex]
            MiddleSection(
                imageRes = enemy.ennemiRessource,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            // 3) BAS : PV ennemi + bouton Attaquer
            FooterSection(
                enemyHp = enemyLives[enemyIndex],
                onAttack = {
                    val currentLife = enemyLives[enemyIndex]
                    val newEnemyLife = currentLife - playerForce

                    if (newEnemyLife <= 0) {
                        // Ennemi vaincu → set 0 puis prochain
                        enemyLives[enemyIndex] = 0
                        enemyIndex = (enemyIndex + 1) % listeEnnemi.size
                    } else {
                        // L’ennemi survit → applique les dégâts
                        enemyLives[enemyIndex] = newEnemyLife

                        // Riposte
                        val newPlayerLife = playerLife - listeEnnemi[enemyIndex].force
                        if (newPlayerLife <= 0) {
                            playerLife = 0
                            Toast.makeText(context, "K.O. ! Retour au début.", Toast.LENGTH_SHORT).show()
                            // Reset inline
                            playerLife = 100
                            playerForce = 10
                            enemyIndex = 0
                            enemyLives.clear()
                            enemyLives.addAll(listeEnnemi.map { it.vie })
                        } else {
                            playerLife = newPlayerLife
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

/* ========== 3 grandes sections (stateless) ========== */

@Composable
fun HeaderSection(vie: Int, force: Int, modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Joueur", style = MaterialTheme.typography.titleMedium)
        Text("Vie : ${kotlin.math.max(0, vie)}")
        Text("Force : $force")
    }
}

@Composable
fun MiddleSection(imageRes: Int, modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(imageRes),
            contentDescription = null,
            modifier = Modifier.size(300.dp), //  même taille pour tous
            contentScale = ContentScale.Fit     //  conserve les proportions
        )
    }
}

@Composable
fun FooterSection(enemyHp: Int, onAttack: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Vie de l’ennemi : ${kotlin.math.max(0, enemyHp)}")
        Spacer(Modifier.height(12.dp))
        Button(onClick = onAttack) { Text("Attaquer") }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PokemonPreview() {
    PokemonAATheme { MonAppli() }
}
