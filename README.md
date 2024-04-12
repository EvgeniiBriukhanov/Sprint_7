# Final_Sprint_7

## Этот проект для тестирования приложения Самокат
- Проверка создания курьера.
- Проверка авторизации курьера. 
- Проверка создание заказа.
- Проверка получения всех заказов

## Для работы проекта необходимо
- JAVA 11
- junit 4.13.2
- Aspectj 1.9.7
- Allure 2.15.0
- rest-assured 5.3.0
- gson 2.10.1

### Модуль взаимодействия Allure и Junit
- allure-junit4
- allure-rest-assured

### Плагин maven-surefire-plugin
- maven-surefire-plugin 2.22.2

### Подключения плагина allure-maven для вызова функций Allure с помощью Maven
- allure-maven 2.10.0

## Для запуска тестов
mvn clean test 
- В папке target появится подпапка allure-results с отчётом Allure.

### Для просмотра отчета Allure
mvn allure:serve
- Запустится веб-сервер Allure, и в браузере откроется вкладка с отчётом



