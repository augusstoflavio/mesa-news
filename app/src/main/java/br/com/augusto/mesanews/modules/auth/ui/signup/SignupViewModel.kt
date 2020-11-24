package br.com.augusto.mesanews.modules.auth.ui.signup

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.augusto.mesanews.R
import br.com.augusto.mesanews.app.data.Result
import br.com.augusto.mesanews.modules.auth.data.LoginRepository
import kotlinx.coroutines.launch

class SignupViewModel(private val loginRepository: LoginRepository): ViewModel() {

    private val _signupForm = MutableLiveData<SignupFormState>()
    val signupFormState: LiveData<SignupFormState> = _signupForm

    private val _signupResult = MutableLiveData<SignupResult>()
    val signupResult: LiveData<SignupResult> = _signupResult

    fun signupDataChanged(name: String, username: String, password: String, passwordConfirmation: String) {
        if (!isUserNameValid(username)) {
            _signupForm.value = SignupFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _signupForm.value = SignupFormState(passwordError = R.string.invalid_password)
        } else if (password != passwordConfirmation) {
            _signupForm.value = SignupFormState(confirmationPasswordError = R.string.invalid_password_confirmation)
        } else {
            _signupForm.value = SignupFormState(isDataValid = true)
        }
    }

    fun signup(name: String, username: String, password: String) {
        viewModelScope.launch {
            try {
                val result = loginRepository.signup(name, username, password)

                if (result is Result.Success) {
                    _signupResult.value = SignupResult(success = true)
                } else {
                    _signupResult.value = SignupResult(error = R.string.signup_failed)
                }
            } catch (e: Exception) {
                _signupResult.value = SignupResult(error = R.string.signup_failed)
            }
        }
    }

    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}