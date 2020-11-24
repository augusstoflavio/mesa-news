package br.com.augusto.mesanews.modules.auth.data

import br.com.augusto.mesanews.app.data.enum.PreferenceEnum
import br.com.augusto.mesanews.app.data.Preferences
import br.com.augusto.mesanews.app.data.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepository(val dataSource: LoginDataSource, val preferences: Preferences) {

    fun isLoggedIn(): Boolean {
        return preferences.getEmail() != null
    }

    suspend fun signin(email: String, password: String): Result<String> {
        return withContext(Dispatchers.IO) {
            val result = dataSource.signin(email, password)

            if (result is Result.Success) {
                savePreferences(email, result.data)
            }

            result
        }
    }

    suspend fun signup(name: String, email: String, password: String): Result<String> {
        return withContext(Dispatchers.IO) {
            val result = dataSource.signup(name, email, password)

            if (result is Result.Success) {
                savePreferences(email, result.data)
            }

            result
        }
    }

    private fun savePreferences(
        email: String,
        token: String
    ) {
        preferences.putEmail(email)
        preferences.putAccessToken(token)
    }

    suspend fun getLoggedIn(): String? {
        return withContext(Dispatchers.IO) {
            return@withContext preferences.getString(PreferenceEnum.EMAIL.name)!!
        }
    }

    private fun clearPreferences() {
        preferences.clear()
    }
}