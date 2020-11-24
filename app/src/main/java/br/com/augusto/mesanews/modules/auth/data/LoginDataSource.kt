package br.com.augusto.mesanews.modules.auth.data

import br.com.augusto.mesanews.app.data.Result
import br.com.augusto.mesanews.modules.auth.services.AuthService

class LoginDataSource(val authService: AuthService) {

    fun signin(email: String, password: String): Result<String> {
        val call = authService.signin(email, password)
        val response = call.execute()

        if (response.isSuccessful) {
            return Result.Success(response.body()!!.token!!)
        } else {
            return Result.Error(Exception("Não foi possível efetuar o login"))
        }
    }

    fun signup(name: String, email: String, password: String): Result<String> {
        val call = authService.signup(name, email, password)
        val response = call.execute()

        if (response.isSuccessful) {
            return Result.Success(response.body()!!.token!!)
        } else {
            return Result.Error(Exception("Não foi possível efetuar o login"))
        }
    }
}