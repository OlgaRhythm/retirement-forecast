# Retirement Forecast (Прогноз выхода на пенсию)

## Описание

Этот проект предназначен для прогнозирования раннего выхода на пенсию на основе загруженных CSV файлов, содержащих в себе персональные данные и сведения о транзакциях. Эти файлы отправляются на сервер, где обрабатываются с помощью ML модели. Затем полученные прогнозы отображаются в интерфейсе веб-приложения.

## Содержание

- [Описание](#описание)
- [Технологии](#технологии)
- [Архитектура](#архитектура)
- [Установка и запуск](#установка-и-запуск)
- [Использование](#использование)
- [Планы по развитию](#планы-по-развитию)

## Технологии

- **Фронтенд**: React, Ant Design
- **Бэкенд**: Spring Boot, Java 17
- **Модель машинного обучения**: Python, PyKAN
  
## Архитектура

### Структура:

// требуется картинка

### Общий процесс:

1. **Загрузка файлов**: Пользователь загружает два CSV файла (личные данные и данные о транзакциях) через веб-интерфейс.
2. **Обработка на сервере**: Бэкенд принимает файлы, отправляет их в ML модуль.
3. **Анализ и прогнозирование**: ML модуль выполняет прогноз и возвращает результаты.
4. **Расчет финансовой нагрузки на фонд в ближайшие пару лет**: На бэкенде выполняется расчет дополнительной финансовой нагрузки на пенсионный фонд.
5. **Отображение**: Бэкенд отправляет результаты на клиентскую сторону для последующей обработки и анализа.

### Описание модели машинного обучения:

Для наших данных мы используем модель Колмогорова-Арнольда вместо классических методов машинного обучения из-за возможности находить не очевидные признаки при сохранении интерпретируемости, благодаря чему без лишних затрат можно модифицировать и масштабировать решение без внесения каких-либо правок в архитектуру модели.

## Установка и запуск
1. **Установите Docker и Docker Compose, если они не установлены**: проверьте установку, открыв консоль и введя следующие строки
   ```
   docker --version
   docker compose --version
   ```
3. **Клонируйте репозиторий**:
   ```
   git clone https://github.com/OlgaRhythm/retirement-forecast.git
   cd retirement-forecast
   ```
5. **Запустите приложение**:
   ```
   docker compose up -d
   ```
6. **Доступ к приложению**: откройте браузер и введите в адресную строку
   ```
   http://localhost:8070
   ```

## Использование
(В качестве примера используются нереальные данные)

1. Выберите файлы с персональными данными и сведениями о транзациях, используя соответствущие кнопки
   ![image](https://github.com/user-attachments/assets/15364b5b-8a11-45e4-b46d-db423acc06ce)
3. Когда оба файла будут выбраны, нажмите на кнопку "Отправить данные на сервер"
   ![image](https://github.com/user-attachments/assets/02641b3a-fadb-4b97-b70a-830721bc250b)
5. В случае получения успешного ответа будет выведена таблица с прогнозами, величина затрат на выплату досрочной пенсии и всего объема пенсионных выплат в период с 2022 до 2024
  ![image](https://github.com/user-attachments/assets/0953573c-356e-46b0-80ae-da95c8259877)


## Планы по развитию

1. С помощью ML мы предскажем, выйдет ли человек на пенсию преждевременно или нет, на основе финансовых операций, пола и других личных данных. Затем, мы анализируем на основе предсказания кто выйдет на пенсию раньше, какая финансовая нагрузка будет на фонд за определенный период времени.
2. Связь ML и фронта через Java. В ML - нейронная сеть использующая обучаемые функции активации. На  бэке java - просчитывается денежная масса выплат фонда клиентам после 2024 года (когда все люди из датасета вышли на пенсию) за определенный период.
3. Уникальность нашего решения в модели, которая может увидеть неочевидные зависимости и в модульности архитектуры, где каждый блок может быть легко заменён или дополнен
