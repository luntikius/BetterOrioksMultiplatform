import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import model.BottomNavItem
import model.ControlEventsScreen
import model.MenuScreen
import model.NewsScreen
import model.NewsViewScreen
import model.NotificationsScreen
import model.ResourcesScreen
import model.ScheduleScreen
import model.SubjectsScreen
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.koin.core.component.getScopeName
import ui.common.ConstructionScreen
import ui.controlEventsScreen.ControlEventsScreen
import ui.loginScreen.LoginScreen
import ui.menuScreen.MenuScreen
import ui.menuScreen.MenuScreenViewModel
import ui.newsScreen.NewsScreen
import ui.newsScreen.newsViewScreen.NewsViewScreen
import ui.resourcesScreen.ResourcesScreen
import ui.scheduleScreen.ScheduleScreen
import ui.scheduleScreen.ScheduleScreenViewModel
import ui.subjectsScreen.SubjectsScreen
import ui.subjectsScreen.SubjectsViewModel

private val BOTTOM_NAV_SCREENS = listOf(
    BottomNavItem.Schedule,
    BottomNavItem.Subjects,
    BottomNavItem.Menu
)

@Composable
fun BetterOrioksApp(
    appViewModel: AppViewModel
) {
    val isAuthorized by appViewModel.isAuthorized.collectAsState(false)

    if (isAuthorized) {
        val navController = rememberNavController()
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = { if (isAuthorized) BottomNavigationBar(navController) }
        ) { paddingValues ->
            AppNavigation(
                navController = navController,
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            )
        }
    } else {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { paddingValues ->
            LoginScreen(
                koinInject(),
                modifier = Modifier.fillMaxSize().padding(paddingValues)
            )
        }
    }
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val scheduleScreenViewModel = koinInject<ScheduleScreenViewModel>()
    val menuScreenViewModel = koinInject<MenuScreenViewModel>()
    val subjectsViewModel = koinInject<SubjectsViewModel>()

    NavHost(
        navController = navController,
        startDestination = ScheduleScreen,
        modifier = modifier
    ) {
        composable<ScheduleScreen> {
            ScheduleScreen(scheduleScreenViewModel)
        }

        composable<SubjectsScreen> {
            SubjectsScreen(navController, subjectsViewModel)
        }

        composable<ControlEventsScreen> { backStackEntry ->
            val route = backStackEntry.toRoute<ControlEventsScreen>()
            ControlEventsScreen(route.subjectId, navController)
        }

        composable<ResourcesScreen> { backStackEntry ->
            val route = backStackEntry.toRoute<ResourcesScreen>()
            ResourcesScreen(
                subjectName = route.subjectName,
                disciplineId = route.subjectId,
                scienceId = route.scienceId,
                navController = navController
            )
        }

        composable<MenuScreen> {
            MenuScreen(navController, menuScreenViewModel)
        }

        composable<NewsScreen> { backstackEntry ->
            val route = backstackEntry.toRoute<NewsScreen>()
            NewsScreen(route.subjectId, navController)
        }

        composable<NewsViewScreen> { backStackEntry ->
            val route = backStackEntry.toRoute<NewsViewScreen>()
            NewsViewScreen(route.id, route.getType(), navController)
        }

        composable<NotificationsScreen> {
            ConstructionScreen(onBackButtonClick = navController::navigateUp)
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
        modifier = Modifier.height(48.dp + getBottomNavBarSize()),
        windowInsets = WindowInsets.navigationBars
    ) {
        BOTTOM_NAV_SCREENS.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painterResource(item.icon),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.primary.copy(0.5f),
                    indicatorColor = MaterialTheme.colorScheme.primary.copy(0.0f)
                ),
                selected = currentRoute == item.screen.getScopeName().toString(),
                onClick = {
                    navController.navigate(item.screen) {
                        launchSingleTop = true
                        restoreState = item.restoreState
                    }
                },
                alwaysShowLabel = false
            )
        }
    }
}

@Composable
fun getBottomNavBarSize(): Dp = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

