package handlers

import platform.UIKit.UIAlertController
import platform.UIKit.UIAlertControllerStyleAlert
import platform.UIKit.UIApplication
import platform.UIKit.UIViewController
import platform.darwin.DISPATCH_TIME_NOW
import platform.darwin.NSEC_PER_SEC
import platform.darwin.dispatch_after
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import platform.darwin.dispatch_time

class IosToastHandler : ToastHandler {
    override fun makeToast(message: String) {
        showToast(message, LONG_TOAST_DURATION)
    }

    override fun makeShortToast(message: String) {
        showToast(message, SHORT_TOAST_DURATION)
    }

    private fun showToast(message: String, duration: Double) {
        dispatch_async(dispatch_get_main_queue()) {
            val alert = UIAlertController.alertControllerWithTitle(
                title = null,
                message = message,
                preferredStyle = UIAlertControllerStyleAlert
            )

            val viewController = getRootViewController()
            viewController.presentViewController(alert, animated = true, completion = null)

            dispatch_after(
                dispatch_time(DISPATCH_TIME_NOW, (duration * NSEC_PER_SEC.toLong()).toLong()),
                dispatch_get_main_queue()
            ) {
                alert.dismissViewControllerAnimated(true, null)
            }
        }
    }

    companion object {
        private const val LONG_TOAST_DURATION = 3.5
        private const val SHORT_TOAST_DURATION = 2.0
    }
}

private fun getRootViewController(): UIViewController {
    return UIApplication.sharedApplication.keyWindow?.rootViewController
        ?: error("No root view controller found")
}