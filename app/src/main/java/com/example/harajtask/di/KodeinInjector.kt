package com.example.harajtask.di

import com.example.harajtask.data.repository.PostRepository
import com.example.harajtask.data.repository.impl.PostRepositoryImpl
import com.example.harajtask.data.source.PostDataSource
import com.example.harajtask.data.source.local.PostDataSourceLocal
import com.example.harajtask.domain.GetPostsUseCase
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton


var KodeinInjector = Kodein {

}