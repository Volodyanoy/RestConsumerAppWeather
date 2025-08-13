# 🌤 RestConsumerAppWeather

Клиентское Java-приложение для взаимодействия с [**RestAppWeather**](https://github.com/Volodyanoy/RestAppWeather) — серверным приложением для сбора метеоданных.

---

## 📌 Описание

RestConsumerAppWeather выполняет следующие задачи:
- Регистрирует новый сенсор в **RestAppWeather**.
- Отправляет на сервер N случайных измерений температуры с информацией о наличии дождя.
- Получает все измерения с сервера.
- Отображает график температур.

---

## ⚙ Технологии

- **Java 17**
- **Spring Web (RestTemplate)**
- **Jackson Databind**
- **SLF4J + Logback** 
- **XChart** 

---

## 📡 Как это работает

1. Генерируется случайное имя сенсора.
2. Отправляется запрос `POST /sensors/registration` на сервер RestAppWeather для регистрации сенсора.
3. На сервер отправляется N измерений `POST /measurements/add` от созданного сенсора:
   ```json
   {
     "value": -12.4,
     "raining": true,
     "sensor": 
               { 
                "name": "Sensor from consumer123" 
               }
   }
4. Выполняется запрос `GET /measurements` для получения всех измерений.
5. Строится график зависимости температуры от номера измерения.
