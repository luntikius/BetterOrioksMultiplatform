package ui.loginScreen

import PlatformOs
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.help
import betterorioks.composeapp.generated.resources.news
import betterorioks.composeapp.generated.resources.notifications
import betterorioks.composeapp.generated.resources.profile
import betterorioks.composeapp.generated.resources.schedule
import betterorioks.composeapp.generated.resources.settings
import betterorioks.composeapp.generated.resources.subjects
import getPlatform
import org.jetbrains.compose.resources.painterResource
import ui.common.Bullet
import ui.common.GradientButton
import ui.common.LargeSpacer
import ui.common.XLargeSpacer

@Composable
fun Slide1(
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1f))
        StaticLogo(showSubtitle = false)
        LargeSpacer()
        Text(
            "Привет!",
            style = MaterialTheme.typography.headlineMedium
        )
        LargeSpacer()
        Text(
            "BetterOrioks — приложение для студентов МИЭТ, сейчас расскажем, что к чему",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.weight(1f))
        GradientButton("Давайте", onClick, modifier = Modifier.padding(horizontal = 32.dp))
        XLargeSpacer()
    }
}

@Composable
fun Slide2(
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1f))
        Text(
            "Вот что у нас есть:",
            style = MaterialTheme.typography.headlineMedium
        )
        XLargeSpacer()
        Bullet(
            title = "Расписание",
            subtitle = "Теперь не нужно запоминать, числитель сейчас или знаменатель",
            image = painterResource(Res.drawable.schedule)
        )
        XLargeSpacer()
        Bullet(
            title = "Информация по предметам",
            subtitle = "Оценки, контрольные мероприятия, ресурсы, преподаватели, экзамены и даже Moodle",
            image = painterResource(Res.drawable.subjects)
        )
        XLargeSpacer()
        Bullet(
            title = "Новости",
            subtitle = "Со всего ОРИОКСа и по каждому предмету",
            image = painterResource(Res.drawable.news)
        )
        if (getPlatform().os == PlatformOs.Android) {
            XLargeSpacer()
            Bullet(
                title = "Уведомления о новых оценках",
                subtitle = "А еще о выходе новостей",
                image = painterResource(Res.drawable.notifications)
            )
        }
        Spacer(Modifier.weight(1f))
        GradientButton("Дальше", onClick, modifier = Modifier.padding(horizontal = 32.dp))
        XLargeSpacer()
    }
}

@Composable
fun Slide3(
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1f))
        Text(
            "Ваши оценки и расписание уже близко!",
            style = MaterialTheme.typography.headlineMedium
        )
        XLargeSpacer()
        Bullet(
            title = "Для работы с BetterOrioks необходимо войти в аккаунт ОРИОКС",
            subtitle = "Мы автоматически загрузим ваше расписание и оценки",
            image = painterResource(Res.drawable.profile)
        )
        XLargeSpacer()
        Bullet(
            title = "Не забудьте заглянуть на экран настроек",
            subtitle = "Подстройте приложение под себя и не забудьте понажимать на все, что нажимается",
            image = painterResource(Res.drawable.settings)
        )
        XLargeSpacer()
        Bullet(
            title = "А если проблемы?",
            subtitle = "Приходите в телеграм-канал, ссылка на него на следующем экране и в меню приложения",
            image = painterResource(Res.drawable.help)
        )
        Spacer(Modifier.weight(1f))
        GradientButton("Ок", onClick, modifier = Modifier.padding(horizontal = 32.dp))
        XLargeSpacer()
    }
}