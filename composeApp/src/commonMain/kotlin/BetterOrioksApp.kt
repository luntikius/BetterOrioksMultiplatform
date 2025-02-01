import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import data.OrioksWebRepository
import model.AppScreens
import model.BottomNavItem
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
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
        startDestination = AppScreens.Schedule.name,
        modifier = modifier
    ) {
        composable(
            route = AppScreens.Schedule.name
        ) {
            ScheduleScreen(scheduleScreenViewModel)
        }

        composable(
            route = AppScreens.Subjects.name
        ) {
            SubjectsScreen(navController, subjectsViewModel)
        }

        composable(
            route = "${AppScreens.ControlEvents.name}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: return@composable
            ControlEventsScreen(id, navController)
        }

        composable(
            route = "${AppScreens.Resources.name}/{discipline_id}/{science_id}/{subject_name}",
            arguments = listOf(
                navArgument("discipline_id") { type = NavType.StringType },
                navArgument("science_id") { type = NavType.StringType },
                navArgument("subject_name") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val disciplineId = backStackEntry.arguments?.getString("discipline_id") ?: return@composable
            val scienceId = backStackEntry.arguments?.getString("science_id") ?: return@composable
            val subjectName = backStackEntry.arguments?.getString("subject_name") ?: return@composable
            ResourcesScreen(
                subjectName = subjectName,
                disciplineId = disciplineId,
                scienceId = scienceId,
                navController = navController
            )
        }

        composable(
            route = AppScreens.Menu.name
        ) {
            MenuScreen(navController, menuScreenViewModel)
        }

        composable(
            route = "${AppScreens.News.name}/{subjectId}",
            arguments = listOf(
                navArgument("subjectId") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) { backstackEntry ->
            val subjectId = backstackEntry.arguments?.getString("subjectId")
            NewsScreen(subjectId, navController)
        }

        composable(
            route = "${AppScreens.NewsView.name}/{id}/{newsType}",
            arguments = listOf(
                navArgument("id") { type = NavType.StringType },
                navArgument("newsType") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: return@composable
            val newsType = OrioksWebRepository.NewsType.valueOf(
                backStackEntry.arguments?.getString("newsType") ?: return@composable
            )
            NewsViewScreen(id, newsType, navController)
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
