package dev.tymoshenko.a6amcafe.app.firebase.firebase

expect class FirebaseHelper() : FirebaseProvider {
    override fun subscribeToTopic(name: String)
    override fun unsubscribeFromTopic(name: String)
}