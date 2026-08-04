package dev.tymoshenko.a6amcafe.app.di

import dev.tymoshenko.a6amcafe.utils.platform.PlatformType
import org.koin.dsl.module

actual val platformModule = module {
    single<PlatformType> {
        PlatformType.ANDROID
    }
}