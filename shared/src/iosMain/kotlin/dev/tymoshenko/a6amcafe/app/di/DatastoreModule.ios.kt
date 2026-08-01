package dev.tymoshenko.a6amcafe.app.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dev.tymoshenko.a6amcafe.app.datastore.DATA_STORE_FILE_NAME
import dev.tymoshenko.a6amcafe.app.datastore.createDataStore
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
actual val datastoreModule = module {
    single<DataStore<Preferences>> {
        createDataStore(
            producePath = {
                val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
                    directory = NSDocumentDirectory,
                    inDomain = NSUserDomainMask,
                    appropriateForURL = null,
                    create = false,
                    error = null
                )
                requireNotNull(documentDirectory).path + "/$DATA_STORE_FILE_NAME"
            }
        )
    }
}