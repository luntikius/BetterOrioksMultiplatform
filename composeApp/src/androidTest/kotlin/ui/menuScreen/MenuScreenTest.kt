package ui.menuScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.test.runner.AndroidJUnit4
import com.studentapp.betterorioks.TestActivity
import data.NotificationsDatabaseRepository
import data.OrioksWebRepository
import data.ScheduleDatabaseRepository
import data.UserPreferencesRepository
import handlers.UrlHandler
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import model.BetterOrioksScreen
import model.settings.SettingsState
import model.settings.Theme
import model.user.UserInfo
import model.user.UserInfoState
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ui.theme.BetterOrioksTheme

@RunWith(AndroidJUnit4::class)
class MenuScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<TestActivity>()

    private lateinit var navController: NavController
    private lateinit var viewModel: MenuScreenViewModel
    private lateinit var userPreferencesRepository: UserPreferencesRepository
    private lateinit var scheduleDatabaseRepository: ScheduleDatabaseRepository
    private lateinit var orioksWebRepository: OrioksWebRepository
    private lateinit var notificationsDatabaseRepository: NotificationsDatabaseRepository
    private lateinit var urlHandler: UrlHandler

    private val userInfo = UserInfo(
        name = "Тестовый Пользователь",
        login = "test123",
        group = "ХОЧУ-5"
    )

    private lateinit var uiStateFlow: MutableStateFlow<MenuScreenUiState>

    private fun setContent(content: @Composable () -> Unit) {
        composeTestRule.setContent {
            BetterOrioksTheme(
                SettingsState(
                    theme = Theme.Light,
                    softenDarkTheme = false,
                    pinkMode = false,
                    coloredBorders = false,
                    enableIosNotifications = false,
                    enableForceNotification = false,
                    showDonationWidget = false,
                )
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    content()
                }
            }
        }
    }

    @Before
    fun setup() {
        navController = mockk(relaxed = true)
        userPreferencesRepository = mockk(relaxed = true)
        scheduleDatabaseRepository = mockk(relaxed = true)
        orioksWebRepository = mockk(relaxed = true)
        notificationsDatabaseRepository = mockk(relaxed = true)
        urlHandler = mockk(relaxed = true)

        uiStateFlow = MutableStateFlow(
            MenuScreenUiState(
                userInfoState = UserInfoState.Success(userInfo),
                iosNotificationsEnabled = true,
                showDonationWidget = true
            )
        )

        viewModel = mockk(relaxed = true)
        every { viewModel.uiState } returns uiStateFlow.asStateFlow()
    }

    @Test
    fun userInfoIsDisplayedCorrectly() {
        setContent {
            MenuScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        composeTestRule.onNodeWithText(userInfo.name).assertIsDisplayed()
        composeTestRule.onNodeWithText("${userInfo.login} · ${userInfo.group}").assertIsDisplayed()
    }

    @Test
    fun navigationButtonsAreDisplayedAndClickable() {
        // Запускаем экран в тестовом окружении
        setContent {
            MenuScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        // Проверяем наличие кнопок навигации
        composeTestRule.onNodeWithContentDescription("Уведомления").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Новости").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Настройки").assertIsDisplayed()

        // Нажимаем на кнопку настроек и проверяем навигацию
        composeTestRule.onNodeWithContentDescription("Настройки").performClick()
        verify {
            navController.navigate(
                route = eq(BetterOrioksScreen.SettingsScreen),
                builder = any()
            )
        }

        composeTestRule.onNodeWithContentDescription("Уведомления").performClick()
        verify {
            navController.navigate(
                route = eq(BetterOrioksScreen.NotificationsScreen),
                builder = any()
            )
        }

        composeTestRule.onNodeWithContentDescription("Новости").performClick()
        verify {
            navController.navigate(
                route = eq(BetterOrioksScreen.NewsScreen(null)),
                builder = any()
            )
        }
    }

    @Test
    fun donationWidgetIsDisplayedAndCanBeHidden() {
        // Запускаем экран в тестовом окружении
        setContent {
            MenuScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        // Проверяем, что виджет пожертвований отображается
        composeTestRule.onNodeWithText("Поддержать").assertIsDisplayed()
        composeTestRule.onNodeWithText("В другой раз").assertIsDisplayed()

        // Нажимаем на кнопку "Next time"
        composeTestRule.onNodeWithText("В другой раз").performClick()

        // Проверяем, что был вызван метод hideDonationWidget
        verify { viewModel.hideDonationWidget() }

        // Обновляем состояние, чтобы скрыть виджет
        uiStateFlow.update { it.copy(showDonationWidget = false) }

        // Проверяем, что виджет больше не отображается
        composeTestRule.onNodeWithText("Поддержать").assertDoesNotExist()
    }

    @Test
    fun logoutButtonShowsAlertAndLogsOut() {
        // Запускаем экран в тестовом окружении
        setContent {
            MenuScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        // Нажимаем на кнопку выхода
        composeTestRule.onNodeWithTag("Exit").performClick()

        // Проверяем, что появился диалог подтверждения
        composeTestRule.onNodeWithTag("ExitPopupButton").assertIsDisplayed()

        // Нажимаем на кнопку подтверждения в диалоге
        composeTestRule.onNodeWithTag("ExitPopupButton").performClick()

        // Проверяем, что был вызван метод logout
        verify { viewModel.logout() }
    }

    @Test
    fun errorStateShowsErrorScreen() {
        // Устанавливаем состояние ошибки
        uiStateFlow.value = MenuScreenUiState(
            userInfoState = UserInfoState.Error(Exception("Тестовая ошибка")),
            iosNotificationsEnabled = true,
            showDonationWidget = true
        )

        // Запускаем экран в тестовом окружении
        setContent {
            MenuScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        // Проверяем, что отображается экран ошибки
        composeTestRule.onNodeWithText("Не удалось загрузить данные").assertIsDisplayed()
        composeTestRule.onNodeWithText("Перезагрузить").assertIsDisplayed()

        // Нажимаем на кнопку перезагрузки
        composeTestRule.onNodeWithText("Перезагрузить").performClick()

        // Проверяем, что был вызван метод getScreenData
        verify { viewModel.getScreenData() }
    }
} 