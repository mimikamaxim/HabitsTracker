package com.example.habitstracker.data.net

import android.os.Handler
import android.os.Looper
import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// Создаем интерфейс для определения методов API
interface ApiService {
    // Аннотация @GET указывает, что запрос будет типа GET
    // В скобках указываем конечный путь к ресурсу
    @GET("posts")
    // Функция getPosts возвращает объект Call, который содержит список постов
    fun getPosts(): Call<List<Post>>
}

// Создаем класс Post для хранения данных о посте
data class Post(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)

// Создаем объект Retrofit для выполнения запросов к серверу
val retrofit = Retrofit.Builder()
    // Указываем базовый URL для всех запросов
    .baseUrl("https://jsonplaceholder.typicode.com/")
    // Указываем конвертер для преобразования JSON в объекты Kotlin
    .addConverterFactory(GsonConverterFactory.create())
    // Указываем клиент OkHttp для настройки сетевого взаимодействия
    .client(OkHttpClient.Builder().build())
    // Собираем объект Retrofit
    .build()

// Создаем объект apiService для доступа к методам API
val apiService = retrofit.create(ApiService::class.java)

fun test () {
// Выполняем асинхронный запрос к серверу для получения списка постов
    apiService.getPosts().enqueue(object : Callback<List<Post>> {
        // Обрабатываем успешный ответ от сервера
        override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
            // Проверяем, что ответ не пустой и содержит данные
            if (response.isSuccessful && response.body() != null) {
                // Получаем список постов из тела ответа
                val posts = response.body()!!
                // Делаем что-то с полученными данными, например, выводим в лог
                Log.d("RetrofitExample", "Posts: $posts")
            }
        }

        // Обрабатываем неуспешный ответ или ошибку от сервера
        override fun onFailure(call: Call<List<Post>>, t: Throwable) {
            // Выводим сообщение об ошибке в лог
            Log.e("RetrofitExample", "Error: ${t.message}")
        }
    })
}

// Создаем объект handler для запуска задач с задержкой
val handler = Handler(Looper.getMainLooper())

// Создаем функцию retryRequest, которая принимает объект call и число retries
fun retryRequest(call: Call<List<Post>>, retries: Int) {
    // Выполняем асинхронный запрос к серверу
    call.enqueue(object : Callback<List<Post>> {
        // Обрабатываем успешный ответ от сервера
        override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
            // Проверяем, что ответ не пустой и содержит данные
            if (response.isSuccessful && response.body() != null) {
                // Получаем список постов из тела ответа
                val posts = response.body()!!
                // Делаем что-то с полученными данными, например, выводим в лог
                Log.d("RetrofitExample", "Posts: $posts")
            }
        }

        // Обрабатываем неуспешный ответ или ошибку от сервера
        override fun onFailure(call: Call<List<Post>>, t: Throwable) {
            // Выводим сообщение об ошибке в лог
            Log.e("RetrofitExample", "Error: ${t.message}")
            // Проверяем, что число попыток не исчерпано
            if (retries > 0) {
                // Задаем промежуток времени для повтора запроса в миллисекундах
                val delay = 5000L
                // Выводим сообщение о повторе запроса в лог
                Log.d("RetrofitExample", "Retrying request in $delay ms")
                // Запускаем функцию retryRequest с уменьшенным числом попыток через заданный промежуток времени
                handler.postDelayed({ retryRequest(call.clone(), retries - 1) }, delay)
            }
        }
    })
}
fun test2() {
    // Вызываем функцию retryRequest с объектом call и числом попыток, например, 3
    retryRequest(apiService.getPosts(), 3)
}