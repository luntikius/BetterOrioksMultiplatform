import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    
    func setRussianLocale() {
        UserDefaults.standard.set(["ru"], forKey: "AppleLanguages")
        UserDefaults.standard.synchronize()
    }
    
    init() {
        setRussianLocale()
        MainViewControllerKt.startKoin()
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
