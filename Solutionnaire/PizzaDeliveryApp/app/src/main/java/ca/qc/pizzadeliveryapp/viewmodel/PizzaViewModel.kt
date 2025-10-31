package ca.qc.pizzadeliveryapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class PizzaViewModel : ViewModel() {
    val pizzaSizeOptions = listOf("Small", "Medium", "Large", "Jumbo") //dropwodn options

    var selectedPizzaSizeOption by mutableStateOf(pizzaSizeOptions.first())

    fun selectPizzaOption(option: String) {
        selectedPizzaSizeOption = option
    }


    val tipOptions = listOf(0, 10, 15, 20) //dropwodn options for tip

    var selectedTipOption by mutableStateOf(tipOptions.first())

    fun selectTipOption(option: Int) {
        selectedTipOption = option
    }

    var total by mutableStateOf(0.0)
        private set

    fun calculateTotal() {
        val basePrice = when (selectedPizzaSizeOption) {
            "Small" -> 10.0
            "Medium" -> 15.0
            "Large" -> 20.0
            "Jumbo" -> 25.0
            else -> 0.0
        }
        total = basePrice + (basePrice * selectedTipOption / 100.0)
    }
}