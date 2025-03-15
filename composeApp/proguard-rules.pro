# Keep all Jetpack Compose classes
-keep class androidx.compose.** { *; }

# Keep Kotlin metadata
-keepattributes *Annotation*

# Keep classes for reflection-based libraries (Kotlin serialization, Ktor, etc.)
-keep class kotlinx.serialization.** { *; }
-keep class io.ktor.** { *; }
-keep class org.koin.** { *; }

# Keep Room database entities and DAO interfaces
-keep class androidx.room.** { *; }

# Keep Android WorkManager dependencies
-keep class androidx.work.** { *; }

# Keep entry point activities
-keep class com.studentapp.betterorioks.MainActivity { *; }

-dontwarn androidx.test.platform.app.InstrumentationRegistry
-dontwarn androidx.core.bundle.Bundle
-dontwarn java.lang.management.ManagementFactory
-dontwarn java.lang.management.RuntimeMXBean