package model.background

enum class BackgroundTaskType(val periodMinutes: Int) {
    SubjectNotifications(15),
    NewsNotifications(180),
}
