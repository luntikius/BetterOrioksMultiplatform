<h1>BetterOrioks (теперь в мультиплатформе)</h1>

<nav>
  <ul>
    <li><a href="#about">О приложении</a></li>
    <li><a href="#support">Поддержать разработку</a></li>
    <li><a href="#how-it-works">Как работает?</a></li>
    <li><a href="#features">Возможности</a></li>
    <li><a href="#privacy">Куда уходят мои данные?</a></li>
    <li><a href="#stack">Стек технологий</a></li>
    <li><a href="#contacts">Контакты</a></li>
  </ul>
</nav>

<h2 id="about">О приложении</h2>
<p>
  Мультиплатформенное приложение-компаньон для мониторинга успеваемости и расписания студентами МИЭТа.
  Предоставляет доступ к информации с сайта <code>orioks.miet.ru</code> в удобном мобильном формате.
</p>

<h2 id="support">Поддержать разработку </h2>
<p>
  О том, почему приложению нужна поддержка и как поддержать <b>BetterOrioks</b> можно почитать <a href="https://luntikius.github.io/BetterOrioksMultiplatform/docs/support">тут</a>
</p>

<h2 id="how-it-works">Как работает?</h2>

<h3>Вход</h3>
<p>
  Приложение обменивает логин и пароль на токен по HTTP. В дальнейшем используется только токен,
  приложение не запоминает ваши логин и пароль. Все данные передаются только между устройством и
  <code>orioks.miet.ru</code>.
</p>

<h3>Расписание, оценки, информация о пользователе</h3>
<p>
  Для обеспечения актуальности данных приложение общается напрямую с <code>schedule.miet.ru</code> и <code>orioks.miet.ru</code>. Приложение   умеет работать, как с ответами в формате Json, который используется для расписания и оценок, так и с HTML, который используется для всего остального.
</p>

<h3>Уведомления</h3>
<p>
  Приложение сохраняет в памяти информацию о текущих оценках и последних новостях. С определенной периодичностью выполняется оптимизированная системными средствами задача, в рамках которой отправляется запрос за свежими данными в <code>orioks.miet.ru</code>, данные с сервера сравниваются с теми, что хронятся локально, после чего на каждое отличие отправляется уведомление.
</p>

<h2 id="features">Возможности</h2>
<ul>
  <li>Расписание (доступно без подключения к интернету)</li>
  <li>Оценки по дисциплинам и КМ</li>
  <li>Полная информация по экзаменам и преподавателям</li>
  <li>Ресурсы, курс moodle и новости по дисциплинам</li>
  <li>Задолженности</li>
  <li>Информация о студенте</li>
  <li>Новости ОРИОКСа</li>
</ul>

<p>Также приложение может отправлять уведомления об изменении оценок и выходе новостей. Правда, пока это работает только на Android.</p>

<h3>Как это выглядит?</h3>
<table>
  <tr>
    <td><img src="https://github.com/luntikius/BetterOrioksMultiplatform/blob/main/docs/images/schedule.png?raw=true" alt="Image schedule"></td>
    <td><img src="https://github.com/luntikius/BetterOrioksMultiplatform/blob/main/docs/images/subjects.png?raw=true" alt="Image subjects"></td>
    <td><img src="https://github.com/luntikius/BetterOrioksMultiplatform/blob/main/docs/images/subject.png?raw=true" alt="Image subject"></td>
    <td><img src="https://github.com/luntikius/BetterOrioksMultiplatform/blob/main/docs/images/menu.png?raw=true" alt="Image menu"></td>
  </tr>
</table>

<h2 id="privacy">Куда уходят мои данные?</h2>
<p>
  Все данные хранятся только на устройстве и передаются только серверам ОРИОКСа. Третьим лицам доступ закрыт.
</p>
<h2 id="stack">Стек технологий</h2>
<p>
  Приложение использует фреймворк Compose Multiplatform, который позволяет делать общие интерфейс и бизнес-логику для Android и Ios.
</p>
<p>
  Для общения с сетью используется Ktor. Самая популярная библиотека, позволяющая работать с сетью, при этом не использует Java код.
</p>
<p>
  Для Di использован Koin. Простая и легковесная библиотека, отлично подходящая для работы с Jetpack Compose.
</p>
<h2 id="contacts">Контакты</h2>
<p>
  Следить за новостями, оставить отзыв или предложить улучшение можно в
  <a href="https://t.me/+YQD5-csbrqk4ZjEy" target="_blank">Телеграм-канале</a>.
</p>
<p>
  VK (бываю тут редко): <a href="https://vk.com/luntikius">vk.com/luntikius</a>
</p>

<h3>Капибара для поднятия настроения</h3>
<img src="https://github.com/luntikius/BetterOrioks/blob/master/img/capy.jpg?raw=true" height="250" alt="Капибара">
