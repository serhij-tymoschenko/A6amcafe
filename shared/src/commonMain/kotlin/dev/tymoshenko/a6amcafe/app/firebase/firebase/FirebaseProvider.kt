package dev.tymoshenko.a6amcafe.app.firebase.firebase

interface FirebaseProvider {
    fun subscribeToTopic(name: String)
    fun unsubscribeFromTopic(name: String)
}