package ui.loginScreen

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
import betterorioks.composeapp.generated.resources.star_shine
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
            "BetterOrioks обновился, сейчас расскажем, что к чему",
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
            "Вот что у нас нового:",
            style = MaterialTheme.typography.headlineMedium
        )
        XLargeSpacer()
        Bullet(
            title = "Обновили дизайн",
            subtitle = "Темы выглядят четче, цвета ярче!",
            image = painterResource(Res.drawable.star_shine)
        )
        XLargeSpacer()
        Bullet(
            title = "Уведомления и их история теперь в отдельной вкладке",
            subtitle = "А еще при нажатии на них вы сразу попадаете на нужный экран",
            image = painterResource(Res.drawable.notifications)
        )
        XLargeSpacer()
        Bullet(
            title = "Появился экран с новостями",
            subtitle = "Да, новости дисциплины теперь тоже можно смотреть!",
            image = painterResource(Res.drawable.news)
        )
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
            title = "После обновления необходимо перезайти в аккаунт ОРИОКС",
            subtitle = "Мы поменяли базу данных, и ваши токены потерялись",
            image = painterResource(Res.drawable.profile)
        )
        XLargeSpacer()
        Bullet(
            title = "А если проблемы?",
            subtitle = "Приходите в телеграм-канал, ссылка на него на следующем экране",
            image = painterResource(Res.drawable.help)
        )
        Spacer(Modifier.weight(1f))
        GradientButton("Ок", onClick, modifier = Modifier.padding(horizontal = 32.dp))
        XLargeSpacer()
    }
}