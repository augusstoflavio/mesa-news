package br.com.augusto.mesanews

import android.app.Application
import br.com.augusto.mesanews.app.data.Preferences
import br.com.augusto.mesanews.app.module.ModuleInterface
import br.com.augusto.mesanews.modules.auth.AuthModule
import br.com.augusto.mesanews.modules.main.MainModule
import br.com.augusto.mesanews.modules.news.NewsModule
import io.realm.Realm
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainAplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin()
        Realm.init(this)
    }

    private fun startKoin() {
        startKoin {
            androidLogger()
            androidContext(this@MainAplication)

            val preferences = Preferences(this@MainAplication)

            applicationContext

            val modulesKoin = getModules().map {
                it.getKoinModule(applicationContext, preferences)
            }.filterNotNull()

            modules(
                modulesKoin
            )
        }
    }

    fun getModules(): List<ModuleInterface> {
        return listOf(
            MainModule(),
            AuthModule(),
            NewsModule()
        )
    }
}