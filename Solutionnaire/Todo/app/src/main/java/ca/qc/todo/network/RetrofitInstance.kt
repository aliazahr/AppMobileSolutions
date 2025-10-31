package ca.qc.todo.network

import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType

/**
 * ApiClient builds and stores one instance of Retrofit for the whole app.
 * It exposes a single "apiService" object that can be used to call endpoints
 * defined in TodoApiService.
 */
object ApiClient {

    // Base URL of the JSONPlaceholder API
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"


    // Build the Retrofit object (only once, thanks to "by lazy")
    private val retrofit by lazy {
        // Configure Kotlin Serialization to ignore unknown JSON fields
        val json = Json { ignoreUnknownKeys = true }

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .build()
    }
    // Generate the implementation of our API interface
    val apiService: TodoApiSercice by lazy {
        retrofit.create(TodoApiSercice::class.java)
    }
}