package dev.tymoshenko.a6amcafe.app.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import dev.tymoshenko.a6amcafe.utils.config.FIRST_LAUNCH_KEY
import dev.tymoshenko.a6amcafe.utils.config.MORE_TRAITS_KEY
import dev.tymoshenko.a6amcafe.utils.config.NOTIFICATIONS_KEY
import dev.tymoshenko.a6amcafe.utils.config.SPECIAL_DROPS_KEY
import dev.tymoshenko.a6amcafe.utils.config.WALLET_KEY
import dev.tymoshenko.a6amcafe.utils.config.WALLET_TYPE_KEY
import okio.Path.Companion.toPath

fun createDataStore(producePath: () -> String): DataStore<Preferences> =
    PreferenceDataStoreFactory.createWithPath(
        produceFile = { producePath().toPath() }
    )

internal const val DATA_STORE_FILE_NAME = "user_preferences.preferences_pb"

object PreferencesKeys {
    val WALLET = stringPreferencesKey(WALLET_KEY)
    val WALLET_TYPE = stringPreferencesKey(WALLET_TYPE_KEY)
    val FIRST_LAUNCH = booleanPreferencesKey(FIRST_LAUNCH_KEY)
    val NOTIFICATIONS = booleanPreferencesKey(NOTIFICATIONS_KEY)
    val SPECIAL_DROPS = booleanPreferencesKey(SPECIAL_DROPS_KEY)
    val MORE_TRAITS = booleanPreferencesKey(MORE_TRAITS_KEY)
}