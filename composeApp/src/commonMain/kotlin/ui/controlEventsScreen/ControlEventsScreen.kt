package ui.controlEventsScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import data.SubjectsRepository
import model.subjects.SubjectsState
import org.koin.compose.koinInject

@Composable
fun ControlEventsScreen(id: String, subjectsRepository: SubjectsRepository = koinInject()) {
    val subjectsStateFlow by subjectsRepository.subjectsState.collectAsState()
    (subjectsStateFlow as? SubjectsState.Success)?.apply {
        val items = this.displaySubjectPerformance[id]
        Column {
            items?.forEach { Text(it.toString()) }
        }
    }
}
