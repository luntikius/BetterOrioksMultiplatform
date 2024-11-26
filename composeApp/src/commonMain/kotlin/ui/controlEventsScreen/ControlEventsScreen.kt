package ui.controlEventsScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ControlEventsScreen(id: String, navController: NavController) {
    val controlEventsViewViewModel = koinViewModel<ControlEventsViewModel>(parameters = { parametersOf(id) })
    val controlEventsUiState by controlEventsViewViewModel.controlEventsUiState.collectAsState()


}
