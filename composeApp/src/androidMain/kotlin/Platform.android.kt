import android.os.Build

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
    override val os: PlatformOs = PlatformOs.Android
}

actual fun getPlatform(): Platform = AndroidPlatform()
