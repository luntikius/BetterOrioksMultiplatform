interface Platform {
    val name: String
    val os: PlatformOs
}

enum class PlatformOs {
    Android,
    Ios
}

expect fun getPlatform(): Platform
