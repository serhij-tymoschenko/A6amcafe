package dev.tymoshenko.a6amcafe.app.di

import io.ktor.client.engine.okhttp.OkHttp
import org.koin.dsl.module

actual val platformEngineModule = module {
    single { OkHttp.create() }
}