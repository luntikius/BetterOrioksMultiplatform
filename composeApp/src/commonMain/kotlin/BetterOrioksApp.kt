import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import model.BetterOrioksScreen
import model.BottomNavItem
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.getKoin
import org.koin.compose.koinInject
import ui.common.ColoredBorders
import ui.common.LocalColoredBorders
import ui.common.getAnimationDuration
import ui.common.getSlideInAnimation
import ui.common.getSlideOutAnimation
import ui.controlEventsScreen.ControlEventsScreen
import ui.loginScreen.LoginScreen
import ui.menuScreen.MenuScreen
import ui.menuScreen.MenuScreenViewModel
import ui.newsScreen.NewsScreen
import ui.newsScreen.newsViewScreen.NewsViewScreen
import ui.notificationsScreen.NotificationsScreen
import ui.resourcesScreen.ResourcesScreen
import ui.scheduleScreen.ScheduleScreen
import ui.scheduleScreen.ScheduleScreenViewModel
import ui.settingsScreen.SettingsScreen
import ui.subjectsScreen.SubjectsScreen
import ui.subjectsScreen.SubjectsViewModel
import ui.theme.BetterOrioksTheme

private val BOTTOM_NAV_SCREENS = listOf(
    BottomNavItem.Schedule,
    BottomNavItem.Subjects,
    BottomNavItem.Menu
)

@Composable
fun BetterOrioksApp(
    appViewModel: AppViewModel,
    openScreenAction: BetterOrioksScreen?
) {
    val state by appViewModel.state.collectAsState()
    var action: BetterOrioksScreen? by remember { mutableStateOf(openScreenAction) }

    CompositionLocalProvider(
        LocalColoredBorders provides ColoredBorders(state.settings.coloredBorders)
    ) {
        BetterOrioksTheme(state.settings) {
            if (state.isAuthorized) {
                val navController = rememberNavController()
                LaunchedEffect(action) {
                    action?.let {
                        navController.navigate(it)
                        action = null
                    }
                }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { if (state.isAuthorized) BottomNavigationBar(navController) }
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
    }
}

inline fun <reified T : Any> NavGraphBuilder.betterOrioksFadeable(
    noinline content: @Composable (AnimatedContentScope.(NavBackStackEntry) -> Unit)
) {
    composable<T>(
        enterTransition = { fadeIn(animationSpec = tween(getAnimationDuration())) },
        exitTransition = { fadeOut(animationSpec = tween(getAnimationDuration())) },
        popEnterTransition = { fadeIn(animationSpec = tween(getAnimationDuration())) },
        popExitTransition = { fadeOut(animationSpec = tween(getAnimationDuration())) },
        content = content
    )
}

inline fun <reified T : Any> NavGraphBuilder.betterOrioksSlidable(
    noinline content: @Composable (AnimatedContentScope.(NavBackStackEntry) -> Unit)
) {
    composable<T>(
        enterTransition = { getSlideInAnimation() },
        exitTransition = { fadeOut(animationSpec = tween(getAnimationDuration())) },
        popEnterTransition = { fadeIn(animationSpec = tween(getAnimationDuration())) },
        popExitTransition = { getSlideOutAnimation() },
        content = { scope ->
            Surface(
                color = MaterialTheme.colorScheme.background
            ) {
                content(scope)
            }
        }
    )
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val koin = getKoin()
    val scheduleScreenViewModel = viewModel { koin.get<ScheduleScreenViewModel>() }
    val menuScreenViewModel = viewModel { koin.get<MenuScreenViewModel>() }
    val subjectsViewModel = viewModel { koin.get<SubjectsViewModel>() }

    NavHost(
        navController = navController,
        startDestination = BetterOrioksScreen.ScheduleScreen,
        modifier = modifier
    ) {
        betterOrioksFadeable<BetterOrioksScreen.ScheduleScreen> {
            ScheduleScreen(scheduleScreenViewModel)
        }

        betterOrioksFadeable<BetterOrioksScreen.SubjectsScreen> {
            SubjectsScreen(navController, subjectsViewModel)
        }

        betterOrioksSlidable<BetterOrioksScreen.ControlEventsScreen> { backStackEntry ->
            val route = backStackEntry.toRoute<BetterOrioksScreen.ControlEventsScreen>()
            ControlEventsScreen(route.subjectId, route.semesterId, navController)
        }

        betterOrioksSlidable<BetterOrioksScreen.ResourcesScreen> { backStackEntry ->
            val route = backStackEntry.toRoute<BetterOrioksScreen.ResourcesScreen>()
            ResourcesScreen(
                subjectName = route.subjectName,
                disciplineId = route.subjectId,
                scienceId = route.scienceId,
                navController = navController
            )
        }

        betterOrioksFadeable<BetterOrioksScreen.MenuScreen> {
            MenuScreen(navController, menuScreenViewModel)
        }

        betterOrioksSlidable<BetterOrioksScreen.NewsScreen> { backstackEntry ->
            val route = backstackEntry.toRoute<BetterOrioksScreen.NewsScreen>()
            NewsScreen(route.subjectId, navController)
        }

        betterOrioksSlidable<BetterOrioksScreen.NewsViewScreen> { backStackEntry ->
            val route = backStackEntry.toRoute<BetterOrioksScreen.NewsViewScreen>()
            NewsViewScreen(route.id, route.getType(), navController)
        }

        betterOrioksSlidable<BetterOrioksScreen.NotificationsScreen> {
            NotificationsScreen(navController)
        }

        betterOrioksSlidable<BetterOrioksScreen.SettingsScreen> {
            SettingsScreen(navController)
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
            val isSelected = currentRoute?.split(".")?.last() == item.screen.name
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
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        navController.navigate(item.screen) {
                            popUpTo(0)
                            launchSingleTop = true
                            restoreState = item.restoreState
                        }
                    }
                },
                alwaysShowLabel = false
            )
        }
    }
}

@Composable
fun getBottomNavBarSize(): Dp = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

