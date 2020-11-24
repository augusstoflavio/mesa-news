package br.com.augusto.mesanews.app.module

import android.content.Context
import br.com.augusto.mesanews.app.data.Preferences
import org.koin.core.module.Module

interface ModuleInterface {

    fun getKoinModule(context: Context, preferences: Preferences): Module?
}