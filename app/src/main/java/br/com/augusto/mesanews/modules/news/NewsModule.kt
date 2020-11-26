package br.com.augusto.mesanews.modules.news

import android.content.Context
import br.com.augusto.mesanews.app.api.RetrofitFactory
import br.com.augusto.mesanews.app.data.Preferences
import br.com.augusto.mesanews.app.module.ModuleAbstract
import br.com.augusto.mesanews.modules.news.data.FavoriteNews
import br.com.augusto.mesanews.modules.news.data.NewsDataSourceFactory
import br.com.augusto.mesanews.modules.news.repository.NewsRepository
import br.com.augusto.mesanews.modules.news.service.NewsService
import br.com.augusto.mesanews.modules.news.ui.viewModel.FavoriteNewsViewModel
import br.com.augusto.mesanews.modules.news.ui.viewModel.NewsViewModel
import br.com.augusto.mesanews.modules.news.ui.viewModel.ShowNewsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

class NewsModule: ModuleAbstract() {

    override fun getKoinModule(context: Context, preferences: Preferences): Module? {
        return module {
            factory<NewsService> {
                val retrofit = RetrofitFactory.withAuthentication(preferences.getAccessToken()!!)
                retrofit.create(NewsService::class.java)
            }

            single {
                NewsDataSourceFactory(get())
            }

            single {
                NewsRepository(get(), get())
            }

            viewModel {
                NewsViewModel(get())
            }

            viewModel {
                ShowNewsViewModel(get())
            }

            viewModel {
                FavoriteNewsViewModel(get())
            }
        }
    }
}