package dev.tymoshenko.a6amcafe

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform