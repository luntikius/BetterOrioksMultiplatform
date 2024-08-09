import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import model.AppScreens
import ui.common.LoadingScreen
import ui.scheduleScreen.ScheduleScreen

@Composable
fun BetterOrioksApp(
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = AppScreens.Schedule.name,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(
                route = AppScreens.Loading.name
            ) {
                LoadingScreen()
            }

            composable(
                route = AppScreens.Schedule.name
            ) {
                ScheduleScreen()
            }
        }
    }
}
