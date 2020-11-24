package br.com.augusto.mesanews.modules.main

import android.content.Context
import br.com.augusto.mesanews.app.data.Preferences
import br.com.augusto.mesanews.app.helper.FotoHelper
import br.com.augusto.mesanews.app.module.ModuleAbstract
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

class MainModule: ModuleAbstract() {

    override fun getKoinModule(context: Context, preferences: Preferences): Module? {
        return module {
            single {
                Preferences(androidContext())
            }

            single {
                FotoHelper()
            }
        }
    }
}