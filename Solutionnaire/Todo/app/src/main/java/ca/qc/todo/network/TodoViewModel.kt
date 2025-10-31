package ca.qc.todo.network

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * TodoViewModel is the bridge between the UI and the network layer.
 * It requests data from the API (through ApiClient) and exposes it
 * to the composables via StateFlow.
 */
class TodoViewModel : ViewModel() {

    val todos = MutableStateFlow<List<Todo>>(emptyList())
    val posts = MutableStateFlow<List<Post>>(emptyList())

    /**
     * Loads the list of todos from the API.
     * Called when the user presses "Fetch Todos".
     */
    fun loadTodos() {
        viewModelScope.launch {
            try {
                todos.value = ApiClient.apiService.getTodos()
            } catch (e: Exception) {
                Log.e("TodoVM", "Error: ${e.message}")
            }
        }
    }

    /**
     * Loads posts for a specific user ID.
     * Called when the user presses "Fetch Posts".
     */
    fun loadPosts(userId: Int) {
        viewModelScope.launch {
            try {
                posts.value = ApiClient.apiService.getPosts(userId)
            } catch (e: Exception) {
                Log.e("TodoVM", "Error: ${e.message}")
            }
        }
    }

}