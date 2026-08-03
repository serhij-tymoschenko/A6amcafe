package dev.tymoshenko.a6amcafe.app.di

import dev.tymoshenko.a6amcafe.data.repos.MashiesFeedRepo
import org.koin.dsl.module

val repoModule = module {
    single<MashiesFeedRepo> {
        MashiesFeedRepo(get())
    }
}