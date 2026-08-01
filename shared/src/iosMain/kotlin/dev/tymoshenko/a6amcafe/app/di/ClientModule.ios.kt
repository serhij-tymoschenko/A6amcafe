package dev.tymoshenko.a6amcafe.app.di

import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module

actual val platformEngineModule = module {
    single { Darwin.create() }
}