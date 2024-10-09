import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import model.AppScreens
import model.BottomNavItem
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import ui.loginScreen.LoginScreen
import ui.scheduleScreen.ScheduleScreen

private val BOTTOM_NAV_SCREENS = listOf(
    BottomNavItem.Schedule,
    BottomNavItem.Menu
)

@Composable
fun BetterOrioksApp(
    appViewModel: AppViewModel,
    navController: NavHostController = rememberNavController()
) {
    val isAuthorized by appViewModel.isAuthorized.collectAsState(false)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { if (isAuthorized) BottomNavigationBar(navController) }
    ) { paddingValues ->
        if (isAuthorized) {
            AppNavigation(
                navController = navController,
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            )
        } else {
            LoginScreen(
                {
                    navController.navigate(AppScreens.Schedule.name) {
                        popUpTo(AppScreens.Schedule.name)
                    }
                },
                koinInject(),
                modifier = Modifier.safeDrawingPadding()
            )
        }
    }

}

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AppScreens.Schedule.name,
        modifier = modifier
    ) {
        composable(
            route = AppScreens.Schedule.name
        ) {
            ScheduleScreen(koinInject())
        }

        composable(
            route = AppScreens.Menu.name
        ) {
            LoginScreen(
                {
                    navController.navigate(AppScreens.Schedule.name) {
                        popUpTo(AppScreens.Schedule.name)
                    }
                },
                koinInject()
            )
        }
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 0.dp,
        modifier = Modifier.height(80.dp)
    ) {
        BOTTOM_NAV_SCREENS.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painterResource(item.icon),
                        contentDescription = null,
                        modifier = Modifier.fillMaxHeight(0.5F)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.primary.copy(0.5f),
                    indicatorColor = MaterialTheme.colorScheme.primary.copy(0.0f)
                ),
                selected = currentRoute == item.screen.name,
                onClick = {
                    navController.navigate(item.screen.name) {
                        launchSingleTop = true
                        restoreState = item.restoreState
                    }
                }
            )
        }
    }
}
