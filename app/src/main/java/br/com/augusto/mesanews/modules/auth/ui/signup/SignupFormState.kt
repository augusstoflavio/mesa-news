package br.com.augusto.mesanews.modules.auth.ui.signup

data class SignupFormState(
    val nameError: Int? = null,
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val confirmationPasswordError: Int? = null,
    val isDataValid: Boolean = false
)