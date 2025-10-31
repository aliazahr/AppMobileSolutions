package ca.qc.pizzadeliveryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.experimental.Experimental
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController //FOR NAVIGATION IMPLEMENTATION
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ca.qc.pizzadeliveryapp.ui.theme.PizzaDeliveryAppTheme
import ca.qc.pizzadeliveryapp.viewmodel.PizzaViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PizzaDeliveryAppTheme {
                AppNav()
            }
        }
    }
}

@Composable
fun AppNav() {
    val navController = rememberNavController()
    val viewModel: PizzaViewModel = viewModel()

    //Apply scaffold either the same way to all pages, or individually to each page
    Scaffold(
        topBar = { Text("Pizza Delivery") },
        bottomBar = { Text("© 2025 Pizza Co.") }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "OrderPizza",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("OrderPizza") {
                OrderPizzaScreen( viewModel, navController)
            }
            composable("OrderSummary") {
                OrderSummaryScreen(viewModel, navController)
            }
        }
    }
}

/*
 ============ Individual scaffold example ===============
 @Composable
fun OrderPizzaScreen(navController: NavController, viewModel: PizzaViewModel = viewModel()) {
    Scaffold(
        topBar = { Text("Order Pizza") }
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            PizzaSizeDropdown(viewModel)
            Button(onClick = { navController.navigate("OrderSummary") }) {
                Text("Confirm Order")
            }
        }
    }
}
*/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderPizzaScreen(viewModel: PizzaViewModel, navController: NavController) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Choisissez la taille")
            PizzaSizeDropdown(viewModel)

            Spacer(Modifier.height(16.dp))

            Text("Choisissez le pourboire")
            TipDropdown(viewModel)

            Spacer(Modifier.height(24.dp))

            Button(onClick = {
                scope.launch {
                    val result = snackbarHostState.showSnackbar(
                        message = "Confirmer la commande ?",
                        actionLabel = "Oui",
                        duration = SnackbarDuration.Short
                    )

                    if (result == SnackbarResult.ActionPerformed) {
                        // Si l'utilisateur confirme
                        viewModel.calculateTotal()
                        navController.navigate("OrderSummary")
                    } else {
                        // Si l'utilisateur ne clique pas sur Oui
                        snackbarHostState.showSnackbar("Commande annulée")
                    }
                }
            }) {
                Text("Confirmer la commande")
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderSummaryScreen(viewModel: PizzaViewModel, navController: NavController) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Résumé de la commande", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(16.dp))
            Text("Taille : ${viewModel.selectedPizzaSizeOption}")
            Text("Pourboire : ${viewModel.selectedTipOption}%")
            Text("Total : ${viewModel.total} $")

            Spacer(Modifier.height(24.dp))

            Button(onClick = {
                scope.launch {
                    val result = snackbarHostState.showSnackbar(
                        message = "Voulez-vous revenir à la page de commande ?",
                        actionLabel = "Oui",
                        duration = SnackbarDuration.Short
                    )

                    if (result == SnackbarResult.ActionPerformed) {
                        snackbarHostState.showSnackbar("Retour à la commande confirmé")
                        navController.popBackStack()
                    } else {
                        snackbarHostState.showSnackbar("Retour annulé")
                    }
                }
            }) {
                Text("Revenir à la commande")
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PizzaDeliveryAppTheme {
        Greeting("Android")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PizzaSizeDropdown( viewModel: PizzaViewModel = viewModel()) {
    var expanded by remember { mutableStateOf(false) }
    val selected = viewModel.selectedPizzaSizeOption // the selected options
    val options = viewModel.pizzaSizeOptions// all the options available

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            readOnly = true,
            value = selected,
            onValueChange = {},
            label = { Text("Salutation") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            colors = TextFieldDefaults.colors(),
            modifier = Modifier.menuAnchor(
                MenuAnchorType.PrimaryNotEditable,
                true
            )
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        viewModel.selectPizzaOption(option)
                        expanded = false
                    }
                )
            }
        }
    }
}


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TipDropdown( viewModel: PizzaViewModel = viewModel()) {
        var expanded by remember { mutableStateOf(false) }
        val selected = viewModel.selectedTipOption.toString() // the selected options
        val options = viewModel.tipOptions// all the options available

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                readOnly = true,
                value = "$selected%",
                onValueChange = {},
                label = { Text("Tip") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                colors = TextFieldDefaults.colors(),
                modifier = Modifier.menuAnchor(
                    MenuAnchorType.PrimaryNotEditable,
                    true
                )
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text("$option%")  },
                        onClick = {
                            viewModel.selectTipOption(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
