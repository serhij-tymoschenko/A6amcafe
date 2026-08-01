package dev.tymoshenko.a6amcafe.app

import android.app.Application
import dev.tymoshenko.a6amcafe.app.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@MyApp)
            androidLogger()
        }
    }
}