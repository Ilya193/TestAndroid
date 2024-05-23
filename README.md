# ilya-komendantov-TestAndroid-vacc-2024  

Разбивка на задачи и оценка по времени выполнения  
1. Создание проекта, подключение необходимых зависимостей (ViewBinding, Dagger 2, Retrofit, kotlinx.serialization, converter kotlinx.serialization, Coil) и деление проекта на модули (app, core, data, features, common)  
Оценочно: 40 минут  
Фактически: 23 минуты  

2. Реализация core:network для получения данных из сети: создание DTO модели и интерфейса для запроса в сеть  
Оценочно: 15 минут  
Фактически: 5 минут  

3. Реализация data:products для вызова методов интерфейса для запроса данных из сети: создание data модели, interface data sourse & data source impl и маппер  
Оценочно: 20 минут  
Фактически: 8 минут   

4. Реализация feature:catalog: деление модуля на пакеты presentation, domain, data, di, создание фрагмента для отображения данных, создание viewmodel для реализации паттерна проектирования MVVM, написание юз кейса  
и репозитория, маппер для слоев data и presentation. Написание пользовательского интерфейса, где отображается список с продуктами, написание di для внедрение зависимостей  
Оценочно: 2 часа  
Фактически: 1 час 28 минут  

5. Реализация app модуля, который внедрит необходимые зависимости для feature:catalog и настроит навигацию через viewmodel. Добавление разрешение на использование интернета, настройка кэширования изображений,  
запуск проекта и проверка, корректно ли отображаются данные  
Оценочно: 1 час 30 минут  
Фактически: 59 минут  

6. Реализация feature:details: деление модуля на пакеты presentation и di, написание пользовательского интерфейса для отображение детальной информации о продукте и реализация di для внедрение зависимостей  
Оценочно: 1 час  
Фактически: 47 минут  

7. Запуск приложения и проверка, верно ли отображаются данные, проверка работы навигации  
Оценочно: 30 минут  
Фактически: 14 минут  
