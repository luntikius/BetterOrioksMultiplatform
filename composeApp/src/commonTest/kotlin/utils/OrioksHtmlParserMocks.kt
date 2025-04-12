package utils

object OrioksHtmlParserMocks {
    val newsListMock = """
    <!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="csrf-param" content="_csrf">
<meta name="csrf-token" content="F2PkelGDZBMSQLXvS-7xE0ilg0tczaxZtqgW1-YOJagjKJAZDtkXQCEXgNgg3qEjDNGzHgyLxy3DyV6_qnlk8Q==">
    <title>ОРИОКС</title>
    <link rel="apple-touch-icon" href="/apple-touch-icon.png" />
    <!-- ОРИОКС 4.5.2+a5a3d40b served by orioks-production-webservice-5dfbc48454-bmsbj (orioks.miet.ru) -->
    <link href="/assets/c6e2fd1/css/bootstrap.css?v=1699081278" rel="stylesheet">
<link href="/libs/bootstrap/bootstrap.min.css?v=1743592894" rel="stylesheet">
<link href="/libs/awesome/css/font-awesome.min.css?v=1743592894" rel="stylesheet">
<link href="/controller/orioks.css?v=1743592894" rel="stylesheet">
<link href="/assets/998e6bac/css/dropdown-x.min.css?v=1699081278" rel="stylesheet"></head>
<body>
<div id="to_top"><span class="glyphicon glyphicon-arrow-up"></span></div>
<nav id="w0" class="navbar-inverse navbar"><div class="container"><div class="navbar-header"><button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#w0-collapse"><span class="sr-only">Toggle navigation</span>
<span class="icon-bar"></span>
<span class="icon-bar"></span>
<span class="icon-bar"></span></button><a class="navbar-brand" href="/">ОРИОКС</a></div><div id="w0-collapse" class="collapse navbar-collapse"><ul id="w1" class="navbar-nav nav"><li><a href="/student/practice/index">Практика</a></li>
<li><a href="/student/student">Обучение</a></li>
<li><a href="/student/homework/list">Домашние задания</a></li>
<li class="dropdown"><a class="dropdown-toggle" href="/" data-toggle="dropdown">Портфолио <span class="caret"></span></a><ul id="w2" class="dropdown-menu"><li><a href="/portfolio/list-uchebnie-project" tabindex="-1">Учебное</a></li>
<li><a href="/portfolio/list-vneuchebnie-project" tabindex="-1">Внеучебное</a></li>
<li><a href="/activity/activist-portfolio/index" tabindex="-1">Внеучебная активность</a></li></ul></li>
<li><a href="/social/project_work/project-list">Проектная работа</a></li>
<li><a href="/student/book">Зачётная книжка</a></li>
<li class="dropdown"><a class="dropdown-toggle" href="/" data-toggle="dropdown">Заявки <span class="caret"></span></a><ul id="w3" class="dropdown-menu"><li><a href="/request/questionnaire/list" tabindex="-1">Обходной лист</a></li>
<li><a href="/request/doc/list" tabindex="-1">Заявления (мат.помощь, соц стипендия, копии док-тов)</a></li>
<li><a href="/request/reference/list" tabindex="-1">Справки</a></li>
<li><a href="/request/holiday/create" tabindex="-1">Последипломный отпуск</a></li></ul></li>
<li><a href="/other/libraries">Электронные библиотеки</a></li>
<li><a href="/support/list">Помощь</a></li></ul><ul id="w4" class="navbar-nav navbar-right nav"><li class="dropdown">
    <a class="dropdown-toggle" data-toggle="dropdown" href="#" title="Уведомления и объявления">
        <i class="nav-icon fa fa-bell" id="notification-bell"></i>
        <span class="badge btn-xs count-messages-top-menu" id="notification-counter"></span>
    </a>
    <div class="dropdown-menu dropdown-menu-left" style="width: 300px;">
        <div class="notifications-list" id="notifications">
        </div>
        <div class="notifications-empty">
            Нет новых уведомлений
        </div>

        <a href="/notification/index">
            <div class="notification-more notification-more-all">
                Отобразить все
            </div>
            <div class="notification-more notification-more-hidden">
                Еще&nbsp;<span id="notification-more-counter"></span>
            </div>
        </a>
    </div>
</li>
<li class="active"><a class="small"><span class="glyphicon glyphicon-calendar"></span> 10 неделя<br> 1 знаменатель</a></li>
<li class="dropdown"><a class="dropdown-toggle" href="#" data-toggle="dropdown">Рыбаков И.В. <span class="caret"></span></a><ul id="w5" class="dropdown-menu"><li><a href="/user/profile" tabindex="-1">Профиль</a></li>
<li><a href="/personal/files/index" tabindex="-1">Личные файлы</a></li>
<li class="divider" style="margin: 0;"></li>
<li><a href="/user/logout" data-method="post" tabindex="-1">Выход</a></li></ul></li></ul></div></div></nav><div id='mask'></div>
<div class="container margin-top">
                            <div>

    <!-- Nav tabs -->
    <ul class="nav nav-tabs margin-bottom" role="tablist">
        <li class="active"><a href="#news" data-toggle="tab">Новости</a></li>
                <li><a href="#contact" data-toggle="tab">Контакты</a></li>
                <li><a href="https://privet-miet.ru/" target="_blank">Справочник студента</a></li>
                        <li><a href="https://www.miet.ru/schedule/" target="_blank">Расписание занятий</a></li>
                <li><a href="/faq/index">Часто задаваемые вопросы</a></li>
                            <li class="pull-right"><a href="https://orioks.miet.ru/storage/d/1369186/53d611c1147a82bcc5c61c52939e0f787c33146b/Studentam_ob_ORIOKS.pdf">Возможности ОРИОКС</a></li>
            </ul>

    <!-- Tab panes -->
    <div class="tab-content">
        <div class="tab-pane active" id="news">    <table class="table table-bordered">
        <tr>
            <th>Дата</th>
            <th>Заголовок</th>
            <th></th>
        </tr>
                    <tr>
                <td>2025-04-11 14:52:23</td>
                <td>Заключительная игра сезона сборной МИЭТ по мини-футболу</td>
                <td><a href="main/view-news?id=700" class="glyphicon glyphicon-eye-open"></a></td>
            </tr>
                    <tr>
                <td>2025-04-10 12:14:14</td>
                <td>Получи 1 000 000 рублей на развитие инновационного проекта</td>
                <td><a href="main/view-news?id=699" class="glyphicon glyphicon-eye-open"></a></td>
            </tr>
            </table>
</div>
                <div class="tab-pane" id="contact"><div class='well'>
    Техническая поддержка рубежного контроля: <a href="mailto:cjob@inbox.ru">cjob@inbox.ru</a><br>
</div>
<table class='table table-condensed'>
    <thead>
    <tr>
        <th>Институт/кафедра</th>
        <th>Аудитория</th>
        <th>Телефон</th>
        <th>Внутренний телефон</th>
        <th>Email</th>
    </tr>
    </thead>
    <tbody>
            <tr>
            <td><a href='http://miet.ru/structure/s/271' target='_blank' title='Военная'>ВК</a></td>
            <td>3314</td>
            <td>(499) 720-89-45</td>
            <td>19-45</td>
            <td>vk@miee.ru</td>
        </tr>
            <tr>
            <td><a href='http://miet.ru/structure/s/243' target='_blank' title='Высшей математики № 1'>ВМ-1</a></td>
            <td>3241</td>
            <td>(499) 720-87-38</td>
            <td>29-38</td>
            <td>hm1@miee.ru</td>
        </tr>
            <tr>
            <td><a href='http://miet.ru/structure/s/242' target='_blank' title='Институт Биомедицинских систем'>Институт БМС</a></td>
            <td>4121</td>
            <td>(499) 720-87-63</td>
            <td>29-85, 25-72</td>
            <td>sersel@miee.ru</td>
        </tr>
            <tr>
            <td><a href='http://miet.ru/structure/s/265' target='_blank' title='Институт высокотехнологичного права, социальных и гуманитарных наук'>Институт ВП СГН</a></td>
            <td>3328</td>
            <td>(499) 720-87-71</td>
            <td>29-71</td>
            <td>right@miee.ru</td>
        </tr>
            <tr>
            <td><a href='https://miet.ru/structure/s/3780' target='_blank' title='Институт интегральной электроники'>Институт ИнЭл</a></td>
            <td></td>
            <td>(499) 710-19-65</td>
            <td>25-51</td>
            <td>dsd@miee.ru</td>
        </tr>
            <tr>
            <td><a href='http://miet.ru/structure/s/316' target='_blank' title='Институт лингвистического и педагогического образования'>Институт ЛПО</a></td>
            <td>3347</td>
            <td>(499) 720-85-48</td>
            <td>28-48</td>
            <td>fldep@miee.ru, demary@miee.ru</td>
        </tr>
            <tr>
            <td><a href='https://miet.ru/structure/s/3223' target='_blank' title='Институт международного образования'>ИМО</a></td>
            <td>1119</td>
            <td>(499) 734-02-64</td>
            <td></td>
            <td>ird@miee.ru</td>
        </tr>
            <tr>
            <td><a href='https://www.miet.ru/structure/s/2774' target='_blank' title='Институт микроприборов и систем управления'>Институт МПСУ</a></td>
            <td></td>
            <td>(499) 732-63-09</td>
            <td>29-55</td>
            <td> pal@olvs.miee.ru</td>
        </tr>
            <tr>
            <td><a href='https://www.miet.ru/structure/s/2776' target='_blank' title='Институт нано- и микросистемной техники'>Институт НМСТ</a></td>
            <td></td>
            <td>(499) 720-87-68</td>
            <td>29-68</td>
            <td> spt@miee.ru</td>
        </tr>
            <tr>
            <td><a href='https://www.miet.ru/structure/s/2777' target='_blank' title='Институт перспективных материалов и технологий'>Институт ПМТ</a></td>
            <td></td>
            <td>(499) 731-22-79</td>
            <td> 710-53-86</td>
            <td> rnd@miee.ru</td>
        </tr>
            <tr>
            <td><a href='https://www.miet.ru/structure/s/3900' target='_blank' title='Институт психологии'>Институт психологии</a></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
        </tr>
            <tr>
            <td><a href='http://miet.ru/structure/s/248' target='_blank' title='Институт системной и программной инженерии и информационных технологий'>Институт СПИНТех</a></td>
            <td>3126</td>
            <td>(499) 720-85-54</td>
            <td>28-54</td>
            <td>incos@miee.ru</td>
        </tr>
            <tr>
            <td><a href='http://miet.ru/structure/s/239' target='_blank' title='Институт Физики и Прикладной Математики'>Институт ФПМ</a></td>
            <td>3331</td>
            <td>(499) 720-85-58</td>
            <td>28-58, 24-33</td>
            <td>fpm@lenta.ru</td>
        </tr>
            <tr>
            <td><a href='http://miet.ru/structure/s/282' target='_blank' title='Институт цифрового дизайна'>Институт ЦД</a></td>
            <td>3250</td>
            <td>(499) 720-85-59</td>
            <td>28-59</td>
            <td>igd@miee.ru</td>
        </tr>
            <tr>
            <td><a href='http://miet.ru/structure/s/1270' target='_blank' title='Информационной безопасности'>ИБ</a></td>
            <td></td>
            <td>(499) 720-87-55</td>
            <td>19-30</td>
            <td>horev@miee.ru</td>
        </tr>
            <tr>
            <td><a href='http://miet.ru/structure/s/264' target='_blank' title='Истории России, государства и права'>ИРГиП</a></td>
            <td>3222</td>
            <td>(499) 720-85-94</td>
            <td>28-94</td>
            <td>fhistory@miee.ru</td>
        </tr>
            <tr>
            <td><a href='https://www.miet.ru/structure/s/3864' target='_blank' title='Колледж электроники и информатики'>Колледж ЭИ</a></td>
            <td></td>
            <td></td>
            <td></td>
            <td>spo@miet.ru</td>
        </tr>
            <tr>
            <td><a href='http://miet.ru/structure/s/260' target='_blank' title='Маркетинга и управления проектами'>МиУП</a></td>
            <td>3239</td>
            <td>(499) 720-87-48</td>
            <td>29-48</td>
            <td>fmr@miee.ru</td>
        </tr>
            <tr>
            <td><a href='' target='_blank' title='Начальной общей военной подготовки'>НОВП</a></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
        </tr>
            <tr>
            <td><a href='https://miet.ru/structure/s/3991' target='_blank' title='Передовая инженерная школа'>ПИШ</a></td>
            <td></td>
            <td></td>
            <td></td>
            <td>pish@miet.ru</td>
        </tr>
            <tr>
            <td><a href='http://miet.ru/structure/s/265' target='_blank' title='Права'>Права</a></td>
            <td>3328</td>
            <td>(499) 720-87-71</td>
            <td>29-71</td>
            <td>right@miee.ru</td>
        </tr>
            <tr>
            <td><a href='http://miet.ru/structure/s/311' target='_blank' title='Русского языка как иностранного'>РкИ</a></td>
            <td>7231</td>
            <td>(499) 734-02-64</td>
            <td>26-80</td>
            <td>ird@miee.ru</td>
        </tr>
            <tr>
            <td><a href='http://miet.ru/structure/s/247' target='_blank' title='Телекоммуникационных систем'>ТКС</a></td>
            <td>4335</td>
            <td>(499) 720-85-82</td>
            <td>28-82</td>
            <td>tcs@miee.ru</td>
        </tr>
            <tr>
            <td><a href='http://miet.ru/structure/s/304' target='_blank' title='Физического воспитания'>ФизВосп</a></td>
            <td>5108</td>
            <td>(499) 720-87-86</td>
            <td>29-86</td>
            <td>kfkmiet@mail.ru</td>
        </tr>
            <tr>
            <td><a href='http://miet.ru/structure/s/263' target='_blank' title='Философии, социологии и политологии'>ФСиП</a></td>
            <td>3219</td>
            <td>(499) 720-87-18</td>
            <td>29-18</td>
            <td>mieephil@rambler.ru</td>
        </tr>
            <tr>
            <td><a href='http://miet.ru/structure/s/259' target='_blank' title='Экономика, менеджмент и финансы'>ЭМФ</a></td>
            <td>3344, 3221</td>
            <td>8 (499) 720-85-73, 8 (499) 720-87-28</td>
            <td>28-73, 25-76, 29 28</td>
            <td>fmn@miee.ru, etf@miee.ru</td>
        </tr>
            <tr>
            <td><a href='http://miet.ru/structure/s/309' target='_blank' title='ЮНЕСКО'>ЮНЕСКО</a></td>
            <td>3206</td>
            <td>(499) 720-90-00</td>
            <td>22-26</td>
            <td>volkov@mail.ru</td>
        </tr>
        </tbody>
</table>
</div>
            </div>

</div>
        </div>
<script src="/assets/1d48a2f8/jquery.js?v=1699081278"></script>
<script src="/assets/f1931d55/yii.js?v=1699081278"></script>
<script src="/controller/orioks.js?v=1743592894"></script>
<script src="/libs/html5shiv.min.js?v=1743592894"></script>
<script src="/libs/respond.min.js?v=1743592894"></script>
<script src="/libs/underscore-min.js?v=1743592894"></script>
<script src="/assets/998e6bac/js/dropdown-x.min.js?v=1699081278"></script>
<script src="/assets/c6e2fd1/js/bootstrap.js?v=1699081278"></script>
<script>jQuery(function ($) {
notifications.load([], 0)
});</script></body>

    """.trimIndent()

    val profileMock = """
        <!DOCTYPE html>
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <meta http-equiv="X-UA-Compatible" content="IE=edge" />
            <meta name="csrf-param" content="_csrf">
        <meta name="csrf-token" content="7IWU2ZgiO82Vat8N26ONOiwF3j6AAbAVrnwNq9ExSBTYzuC6x3hInqY96jqwk90KaHHua9BH22HbHUXDnUYJTQ==">
            <title>ОРИОКС</title>
            <link rel="apple-touch-icon" href="/apple-touch-icon.png" />
            <!-- ОРИОКС 4.5.2+a5a3d40b served by orioks-production-webservice-5dfbc48454-bmsbj (orioks.miet.ru) -->
            <link href="/assets/c6e2fd1/css/bootstrap.css?v=1699081278" rel="stylesheet">
        <link href="/libs/bootstrap/bootstrap.min.css?v=1743592894" rel="stylesheet">
        <link href="/libs/awesome/css/font-awesome.min.css?v=1743592894" rel="stylesheet">
        <link href="/controller/orioks.css?v=1743592894" rel="stylesheet">
        <link href="/assets/998e6bac/css/dropdown-x.min.css?v=1699081278" rel="stylesheet"></head>
        <body>
        <div id="to_top"><span class="glyphicon glyphicon-arrow-up"></span></div>
        <nav id="w2" class="navbar-inverse navbar"><div class="container"><div class="navbar-header"><button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#w2-collapse"><span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span></button><a class="navbar-brand" href="/">ОРИОКС</a></div><div id="w2-collapse" class="collapse navbar-collapse"><ul id="w3" class="navbar-nav nav"><li><a href="/student/practice/index">Практика</a></li>
        <li><a href="/student/student">Обучение</a></li>
        <li><a href="/student/homework/list">Домашние задания</a></li>
        <li class="dropdown"><a class="dropdown-toggle" href="/" data-toggle="dropdown">Портфолио <span class="caret"></span></a><ul id="w4" class="dropdown-menu"><li><a href="/portfolio/list-uchebnie-project" tabindex="-1">Учебное</a></li>
        <li><a href="/portfolio/list-vneuchebnie-project" tabindex="-1">Внеучебное</a></li>
        <li><a href="/activity/activist-portfolio/index" tabindex="-1">Внеучебная активность</a></li></ul></li>
        <li><a href="/social/project_work/project-list">Проектная работа</a></li>
        <li><a href="/student/book">Зачётная книжка</a></li>
        <li class="dropdown"><a class="dropdown-toggle" href="/" data-toggle="dropdown">Заявки <span class="caret"></span></a><ul id="w5" class="dropdown-menu"><li><a href="/request/questionnaire/list" tabindex="-1">Обходной лист</a></li>
        <li><a href="/request/doc/list" tabindex="-1">Заявления (мат.помощь, соц стипендия, копии док-тов)</a></li>
        <li><a href="/request/reference/list" tabindex="-1">Справки</a></li>
        <li><a href="/request/holiday/create" tabindex="-1">Последипломный отпуск</a></li></ul></li>
        <li><a href="/other/libraries">Электронные библиотеки</a></li>
        <li><a href="/support/list">Помощь</a></li></ul><ul id="w6" class="navbar-nav navbar-right nav"><li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#" title="Уведомления и объявления">
                <i class="nav-icon fa fa-bell" id="notification-bell"></i>
                <span class="badge btn-xs count-messages-top-menu" id="notification-counter"></span>
            </a>
            <div class="dropdown-menu dropdown-menu-left" style="width: 300px;">
                <div class="notifications-list" id="notifications">
                </div>
                <div class="notifications-empty">
                    Нет новых уведомлений
                </div>

                <a href="/notification/index">
                    <div class="notification-more notification-more-all">
                        Отобразить все
                    </div>
                    <div class="notification-more notification-more-hidden">
                        Еще&nbsp;<span id="notification-more-counter"></span>
                    </div>
                </a>
            </div>
        </li>
        <li class="active"><a class="small"><span class="glyphicon glyphicon-calendar"></span> 10 неделя<br> 1 знаменатель</a></li>
        <li class="dropdown"><a class="dropdown-toggle" href="#" data-toggle="dropdown">Рыбаков И.В. <span class="caret"></span></a><ul id="w7" class="dropdown-menu"><li><a href="/user/profile" tabindex="-1">Профиль</a></li>
        <li><a href="/personal/files/index" tabindex="-1">Личные файлы</a></li>
        <li class="divider" style="margin: 0;"></li>
        <li><a href="/user/logout" data-method="post" tabindex="-1">Выход</a></li></ul></li></ul></div></div></nav><div id='mask'></div>
        <div class="container margin-top">
                                    <div class="panel panel-info">
            <div class="panel-heading">
                <h3 class="panel-title">
                    Рыбаков Иван Владимирович                    </h3>
            </div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-xs-3">
                        <img class="img-responsive" src="/images/logomiet_hq.jpg" alt="Логотип МИЭТ">
                    </div>
                    <div class=" col-xs-9">
                                            <table class="table table-user-information">
                                <tbody>
                                <tr>
                                    <td>Идентификатор (логин)</td>
                                    <td>8211720</td>
                                </tr>
                                <tr>
                                    <td>E-mail</td>
                                    <td>
                                                                        <a href="mailto:luntikius@gmail.com">
                                            luntikius@gmail.com                                </a>
                                                                                <span class="label label-success">Подтвержден</span>
                                                                                                    </td>
                                </tr>
                                </tbody>
                            </table>

                                                    <div class="form-group">
                                    <a href="/user/update-email" class="btn btn-primary">Изменить адрес e-mail</a>
                                <a class="btn btn-danger pull-right" href="/user/terminate-sessions" data-method="POST" data-confirm="Все сеансы на всех устройствах, включая используемое сейчас, будут завершены, и Вам потребуется заново авторизоваться. Продолжить?">Завершить все сеансы</a>                        </div>
                                                                                                    <form action="/user/profile" method="post">
        <input type="hidden" name="_csrf" value="7IWU2ZgiO82Vat8N26ONOiwF3j6AAbAVrnwNq9ExSBTYzuC6x3hInqY96jqwk90KaHHua9BH22HbHUXDnUYJTQ==">                    <div class="form-group">
                                                            <button type="submit" class="btn btn-primary" name="gia_register">Зарегистрироваться на ГИА</button>                                            </div>
                            </form>                            </div>
                </div>
            </div>
            <div class="panel-footer">
                &nbsp;
                <p class="pull-right">Данные обновлены: 12 апреля 2025 г. в 15:50:54 GMT+3</p>
            </div>
        </div>

        <div id="p0" data-pjax-container="" data-pjax-push-state data-pjax-timeout="1000"><div class="panel panel-info">
            <div class="panel-heading">
                <h3 class="panel-title">Назначения ролей и прав</h3>
            </div>
            <div class="panel-body">
                <div id="w0" class="grid-view">
        <table class="table table-hover table-condensed"><thead>
        <tr><th>Название</th><th>Дата назначения</th><th>Дата истечения полномочий</th><th class="action-column">&nbsp;</th></tr>
        </thead>
        <tbody>
        <tr data-key="student_1242"><td>Студент: ПИН-45</td><td>5 авг. 2024 г., 15:51:16</td><td><i>Бессрочно</i></td><td></td></tr>
        </tbody></table>
        </div>    </div>
        </div>
        </div>
            <div class="panel panel-info">
                <div class="panel-heading">
                    <h3 class="panel-title">Траектории обучения (учетные записи студента)</h3>
                </div>
                <div class="panel-body">
                    <div id="w1" class="grid-view">
        <table class="table table-hover table-condensed"><thead>
        <tr><th>ID</th><th>Cтуденческий билет №</th><th>Группа</th><th>Учебный план</th><th>Активная</th></tr>
        </thead>
        <tbody>
        <tr class="" title="" data-key="0"><td>22542</td><td>8211720</td><td>ПИН-45 (2021 г., бакалавры)</td><td>ПБ: 2021 ПИН-5 ПИН-6 09.03.04 Программные компоненты информационных систем (Институт СПИНТех)<br /><small></small></td><td>Да</td></tr>
        </tbody></table>
        </div>        </div>
            </div>

                </div>
        <script src="/assets/1d48a2f8/jquery.js?v=1699081278"></script>
        <script src="/assets/f1931d55/yii.js?v=1699081278"></script>
        <script src="/assets/f1931d55/yii.gridView.js?v=1699081278"></script>
        <script src="/assets/abf15d04/jquery.pjax.js?v=1699082571"></script>
        <script src="/controller/orioks.js?v=1743592894"></script>
        <script src="/libs/html5shiv.min.js?v=1743592894"></script>
        <script src="/libs/respond.min.js?v=1743592894"></script>
        <script src="/libs/underscore-min.js?v=1743592894"></script>
        <script src="/assets/998e6bac/js/dropdown-x.min.js?v=1699081278"></script>
        <script src="/assets/c6e2fd1/js/bootstrap.js?v=1699081278"></script>
        <script>jQuery(function (${'$'}) {
        jQuery('#w0').yiiGridView({"filterUrl":"\/user\/profile","filterSelector":"#w0-filters input, #w0-filters select","filterOnFocusOut":true});

        jQuery(document).off("submit", "#p0 form[data-pjax]").on("submit", "#p0 form[data-pjax]", function (event) {jQuery.pjax.submit(event, {"push":true,"replace":false,"timeout":1000,"scrollTo":false,"container":"#p0"});});
        jQuery('#w1').yiiGridView({"filterUrl":"\/user\/profile","filterSelector":"#w1-filters input, #w1-filters select","filterOnFocusOut":true});
        notifications.load([], 0)
        });</script></body>

    """.trimIndent()

    val resourcesMock = """
        <div class="list-group">
            <div class="list-group-item bg-grey pointer">Категория 1</div>
            <div class="panel-collapse collapse">
                <div>
                    <a href="/resource1">Ресурс 1</a>
                    <span class="label">Описание 1</span>
                </div>
            </div>
        </div>
    """.trimIndent()

    val newsMock = """
        <h3>Дата создания:</h3>
        12-04-2024 10:00:00
        <h3>Заголовок:</h3>
        Тестовая новость
        <h3>Тело новости:</h3>
        <p>Первый абзац</p>
        <p>Второй абзац с <a href="https://example.com">ссылкой</a></p>
        <h3>Прикреплённые файлы:</h3>
        <a href="/file1.pdf">file1.pdf</a>
    """.trimIndent()

    val csrfMock = """<input type="hidden" name="_csrf" value="test-csrf-token">""".trimIndent()
}