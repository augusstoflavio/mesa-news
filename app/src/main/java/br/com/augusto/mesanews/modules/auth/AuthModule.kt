package br.com.augusto.mesanews.modules.auth

import android.content.Context
import br.com.augusto.mesanews.app.api.RetrofitFactory
import br.com.augusto.mesanews.app.data.Preferences
import br.com.augusto.mesanews.app.module.ModuleAbstract
import br.com.augusto.mesanews.modules.auth.data.LoginDataSource
import br.com.augusto.mesanews.modules.auth.data.LoginRepository
import br.com.augusto.mesanews.modules.auth.services.AuthService
import br.com.augusto.mesanews.modules.auth.ui.signin.SigninViewModel
import br.com.augusto.mesanews.modules.auth.ui.signup.SignupViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

class AuthModule: ModuleAbstract() {

    override fun getKoinModule(context: Context, preferences: Preferences): Module? {
        return module {
            factory<AuthService> {
                val retrofit = RetrofitFactory.withoutAuthentication()
                retrofit.create(AuthService::class.java)
            }

            single {
                LoginDataSource(get())
            }

            single {
                LoginRepository(get(), get())
            }

            viewModel {
                SigninViewModel(get())
            }

            viewModel {
                SignupViewModel(get())
            }
        }
    }
}