import platform.UIKit.UIDevice

class IOSPlatform : Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
    override val os: PlatformOs = PlatformOs.Ios
}

actual fun getPlatform(): Platform = IOSPlatform()
