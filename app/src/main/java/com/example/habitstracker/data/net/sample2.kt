package com.example.habitstracker.data.net

// Импорты
import android.util.Log
import kotlinx.coroutines.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// Интерфейс для определения API
interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<User>
}

// Класс для представления данных пользователя
data class User(
    val id: Int,
    val name: String,
    val email: String
)

// Класс для реализации перехватчика повторов
class RetryInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response = chain.proceed(request)
        var tryCount = 0
        while (!response.isSuccessful && tryCount < 3) {
            Log.d("RetryInterceptor", "Request failed - $tryCount")
            tryCount++
            // Задержка перед повтором
            Thread.sleep(1000)
            response = chain.proceed(request)
        }
        return response
    }
}

// Функция для создания клиента OkHttp с перехватчиком повторов
fun createOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(RetryInterceptor())
        .build()
}

// Функция для создания сервиса Retrofit с клиентом OkHttp и конвертером Gson
fun createApiService(): ApiService {
    return Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .client(createOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)
}

// Функция для запуска корутины и вызова API
fun fetchUsers() {
    // Создаем сервис API
    val apiService = createApiService()
    // Создаем область видимости корутины с диспетчером IO
    val scope = CoroutineScope(Dispatchers.IO)
    // Запускаем корутину в области видимости
    scope.launch {
        try {
            // Вызываем API и получаем список пользователей
            val users = apiService.getUsers()
            // Переключаемся на диспетчер Main для обновления UI
            withContext(Dispatchers.Main) {
                // Выводим количество пользователей в лог
                Log.d("fetchUsers", "Got ${users.size} users")
            }
        } catch (e: Exception) {
            // Обрабатываем исключение в случае ошибки API
            Log.e("fetchUsers", "Error: ${e.message}")
        }
    }
}
//res
//(1) https://github.com/zaihuishou/Kotlin-Coroutine-Retrofit-Okhttp-Sample. https://github.com/zaihuishou/Kotlin-Coroutine-Retrofit-Okhttp-Sample.
//(2) https://stackoverflow.com/questions/60633327. https://stackoverflow.com/questions/60633327/cancellation-in-coroutines-and-okhttp.