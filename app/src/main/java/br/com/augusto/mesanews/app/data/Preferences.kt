package br.com.augusto.mesanews.app.data

import android.content.Context
import br.com.augusto.mesanews.app.data.enum.PreferenceEnum

class Preferences(private val context: Context) {

    val preferences = context.getSharedPreferences("shared", Context.MODE_PRIVATE)

    fun putEmail(token: String) {
        return this.putString(PreferenceEnum.EMAIL.name, token)
    }

    fun getEmail(): String? {
        return this.getString(PreferenceEnum.EMAIL.name)
    }

    fun putAccessToken(token: String) {
        return this.putString(PreferenceEnum.ACCESS_TOKEN.name, token)
    }

    fun getAccessToken(): String? {
        return this.getString(PreferenceEnum.ACCESS_TOKEN.name)
    }

    fun putString(name: String?, value: String?) {
        val edit = preferences.edit()
        edit.putString(name, value!!)
        edit.apply()
    }

    fun getString(name: String): String? {
        if (!this.has(name)) {
            return null
        }
        return preferences.getString(name, "")
    }

    fun has(name: String?): Boolean {
        return preferences.contains(name)
    }

    fun clear() {
        val edit = preferences.edit()
        edit.clear()
        edit.apply()
    }
}