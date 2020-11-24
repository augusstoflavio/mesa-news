package br.com.augusto.mesanews.modules.auth.ui.signin

/**
 * Data validation state of the login form.
 */
data class SigninFormState(val usernameError: Int? = null,
                           val passwordError: Int? = null,
                           val isDataValid: Boolean = false)