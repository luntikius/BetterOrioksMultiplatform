import androidx.lifecycle.ViewModel
import data.UserPreferencesRepository

class AppViewModel(
    preferencesRepository: UserPreferencesRepository
) : ViewModel() {
    val isAuthorized = preferencesRepository.isAuthorized
}