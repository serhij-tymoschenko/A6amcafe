package dev.tymoshenko.a6amcafe.app.di

import dev.tymoshenko.a6amcafe.app.firebase.firebase.FirebaseHelper
import org.koin.dsl.module

val firebaseModule = module {
    single<FirebaseHelper> {
        FirebaseHelper()
    }
}