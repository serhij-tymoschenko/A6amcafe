package dev.tymoshenko.a6amcafe.app.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dev.tymoshenko.a6amcafe.app.datastore.DATA_STORE_FILE_NAME
import dev.tymoshenko.a6amcafe.app.datastore.createDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val datastoreModule = module {
    single<DataStore<Preferences>> {
        createDataStore(
            producePath = { androidContext().filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath }
        )
    }
}