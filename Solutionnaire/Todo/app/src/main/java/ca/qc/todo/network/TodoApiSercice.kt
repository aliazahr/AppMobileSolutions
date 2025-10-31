package ca.qc.todo.network

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface defining all endpoints used by Retrofit.
 * Each function corresponds to one HTTP request.
 *
 * Retrofit will automatically implement this interface
 * and return the decoded objects (thanks to @Serializable).
 */
interface TodoApiSercice {
    /**
     * GET /todos
     * Returns a list of all Todo items.
     */
    @GET("todos")
    suspend fun getTodos(): List<Todo>

    /**
     * GET /posts?userId=1
     * Returns all posts belonging to a specific user.
     * The @Query annotation appends ?userId=X to the URL.
     */
    @GET("posts")
    suspend fun getPosts(@Query("userId") userId: Int): List<Post>
}