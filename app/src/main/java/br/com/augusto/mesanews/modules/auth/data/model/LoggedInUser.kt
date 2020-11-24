package br.com.augusto.mesanews.modules.auth.data.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
        val userId: Long,
        val userName: String,
        val accessToken: String,
        val tokenType: String,
        val userEmail: String
)