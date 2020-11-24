package br.com.augusto.mesanews.modules.auth.ui.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.lifecycle.viewModelScope
import br.com.augusto.mesanews.modules.auth.data.LoginRepository
import br.com.augusto.mesanews.app.data.Result

import br.com.augusto.mesanews.R
import kotlinx.coroutines.launch

class SigninViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _signinForm = MutableLiveData<SigninFormState>()
    val signinFormState: LiveData<SigninFormState> = _signinForm

    private val _signinResult = MutableLiveData<SigninResult>()
    val signinResult: LiveData<SigninResult> = _signinResult

    init {
        if (loginRepository.isLoggedIn()) {
            _signinResult.value = SigninResult(success = true)
        }
    }

    fun signin(username: String, password: String) {
        viewModelScope.launch {
            try {
                val result = loginRepository.signin(username, password)

                if (result is Result.Success) {
                    _signinResult.value = SigninResult(success = true)
                } else {
                    _signinResult.value = SigninResult(error = R.string.signin_failed)
                }
            } catch (e: Exception) {
                _signinResult.value = SigninResult(error = R.string.signin_failed)
            }
        }
    }

    fun signinDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _signinForm.value = SigninFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _signinForm.value = SigninFormState(passwordError = R.string.invalid_password)
        } else {
            _signinForm.value = SigninFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}