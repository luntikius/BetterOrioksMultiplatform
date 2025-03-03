import SwiftUI
import ComposeApp
import BackgroundTasks

@main
struct iOSApp: App {
    
    
    private let backgroundWorker = IosBackgroundWorker()

    private func registerBackgroundTasks() {
        
        let newsTask = BGTaskScheduler.shared.register(
            forTaskWithIdentifier: "com.luntikius.betterorioks.NewsNotifications",
            using: nil
        ) { task in
            self.handleBackgroundTask(task: task as! BGProcessingTask, taskType: .newsnotifications)
        }
        if (newsTask == true) { print("registered task \(BackgroundTaskType.newsnotifications)") }
        
        let scheduleTask = BGTaskScheduler.shared.register(
            forTaskWithIdentifier: "com.luntikius.betterorioks.SubjectNotifications",
            using: nil
        ) { task in
            self.handleBackgroundTask(task: task as! BGAppRefreshTask, taskType: .subjectnotifications)
        }
        if (scheduleTask == true) { print("registered task \(BackgroundTaskType.subjectnotifications)") }
    }

    private func handleBackgroundTask(
        task: BGTask,
        taskType: BackgroundTaskType
    ) {
        print("Executing \(taskType) background task.")
        
        backgroundWorker.executeTask(taskType: taskType)
        
        task.setTaskCompleted(success: true)
    }
    
    func setRussianLocale() {
        UserDefaults.standard.set(["ru"], forKey: "AppleLanguages")
        UserDefaults.standard.synchronize()
    }
    
    init() {
        setRussianLocale()
        MainViewControllerKt.startKoin()
        registerBackgroundTasks()
        NotificationManager.shared.configure()
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
