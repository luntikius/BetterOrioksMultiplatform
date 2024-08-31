import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import data.DatabaseRepository
import data.MietWebRepository
import database.ScheduleDao
import model.AppScreens
import model.BottomNavItem
import org.jetbrains.compose.resources.painterResource
import ui.common.LoadingScreen
import ui.scheduleScreen.ScheduleScreen
import ui.scheduleScreen.ScheduleScreenViewModel

private val BOTTOM_NAV_SCREENS = listOf(
    BottomNavItem.Schedule,
    BottomNavItem.Menu
)

@Composable
fun BetterOrioksApp(
    scheduleDao: ScheduleDao,
    navController: NavHostController = rememberNavController()
) {
    val databaseRepository = DatabaseRepository(scheduleDao)
    val mietWebRepository = MietWebRepository()
    val scheduleScreenViewModel by remember {
        mutableStateOf(ScheduleScreenViewModel(databaseRepository, mietWebRepository))
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { BottomNavigationBar(navController) }
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
                ScheduleScreen(scheduleScreenViewModel)
            }

            composable(
                route = AppScreens.Menu.name
            ) {
                LoadingScreen()
            }
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
        modifier = Modifier.fillMaxHeight(0.09F)
    ) {
        BOTTOM_NAV_SCREENS.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(painterResource(item.icon), contentDescription = null, modifier = Modifier.fillMaxHeight(0.5F))
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
