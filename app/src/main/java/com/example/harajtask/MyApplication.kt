package com.example.harajtask

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.harajtask.data.repository.PostRepository
import com.example.harajtask.data.repository.impl.PostRepositoryImpl
import com.example.harajtask.data.source.PostDataSource
import com.example.harajtask.data.source.local.PostDataSourceLocal
import com.example.harajtask.domain.GetPostsUseCase
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.TT
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import timber.log.Timber

class MyApplication : Application(), KodeinAware{

    override val kodein by Kodein.lazy {
        bind<PostDataSource>() with singleton { PostDataSourceLocal(this@MyApplication.applicationContext) }
        bind<PostRepository>() with provider { PostRepositoryImpl(instance()) }
        bind<GetPostsUseCase>() with provider { GetPostsUseCase(instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}
class KodeinViewModelFactory(val kodein: Kodein) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        kodein.direct.Instance(TT(modelClass))
}