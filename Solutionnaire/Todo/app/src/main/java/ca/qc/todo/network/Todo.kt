package ca.qc.todo.network

// Kotlin serialization imports
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/*=================== REMINDER PLUGINS IMPLEMENTATION ==================
In build.gradle.kts:
    Lets add in the plugins:
        id("org.jetbrains.kotlin.plugin.serialization") version "2.0.21"
    Lets add dependencies:
        // Retrofit & Serialization
            implementation("com.squareup.retrofit2:retrofit:3.0.0")
            implementation("com.squareup.retrofit2:converter-kotlinx-serialization:3.0.0")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")

        // ViewModel
            implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.4")

    And finally authorizations in the manifest.xml :
        <uses-permission android:name="android.permission.INTERNET" />
*/

/**
 * A single Todo item returned by the API.
 * The @Serializable annotation tells Kotlin that this class can be
 * automatically converted from / to JSON using the Kotlin serialization library.
 */
@Serializable
data class Todo(
    val userId: Int,   // ID of the user who owns the task
    val id: Int,       // Unique identifier of the todo

    // The JSON field from the API is called "title"
    // but we want to call it "titre" in our app
    @SerialName("title")
    val titre: String?,

    // The JSON field is "completed" — mapped to "complete" here
    @SerialName("completed")
    val complete: Boolean?
)

/**
 * Represents a user Post returned by the API.
 */
@kotlinx.serialization.Serializable
data class Post(
    val userId: Int,  // ID of the post author
    val id: Int,      // Unique ID of the post
    val title: String,
    val body: String
)