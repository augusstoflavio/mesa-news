package br.com.augusto.mesanews.modules.auth.ui.signin

/**
 * Authentication result : success (user details) or error message.
 */
data class SigninResult(
        val success: Boolean? = null,
        val error: Int? = null
)