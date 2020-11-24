package br.com.augusto.mesanews.app.module

import android.content.Context
import br.com.augusto.mesanews.app.data.Preferences
import org.koin.core.module.Module

abstract class ModuleAbstract: ModuleInterface {

    override fun getKoinModule(context: Context, preferences: Preferences): Module? {
        return null
    }
}