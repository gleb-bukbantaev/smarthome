import com.example.myapplication.data.TokenRequest
import com.example.myapplication.data.*
import com.example.myapplication.web.ApiService
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WebClient {


    val gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()

    var logging: HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    val okhttp = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()


    val api = Retrofit.Builder()
        .client(okhttp)
        .baseUrl("https://ms.newtonbox.ru/smarthome3/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(ApiService::class.java)

    suspend fun getLightState(): LightState {
        return withContext(Dispatchers.IO) {
            api.getLightState()
        }
    }

    suspend fun setLightState(state: LightState) {
        return withContext(Dispatchers.IO) {
            api.setLightState(state)
        }
    }

    suspend fun getHumidityState(): HumidityState {
        return withContext(Dispatchers.IO) {
            api.getHumidityState()
        }
    }

    suspend fun setHumidityState(state: HumidityState) {
        return withContext(Dispatchers.IO) {
            api.setHumidityState(state)
        }
    }

    /*suspend fun getCameraState(): CameraState {
        return withContext(Dispatchers.IO) {
            api.getCameraState()
        }
    }*/

    suspend fun getLockState(): LockState {
        return withContext(Dispatchers.IO) {
            api.getLockState()
        }
    }

    suspend fun setLockState(state: LockState) {
        return withContext(Dispatchers.IO) {
            api.setLockState(state)
        }
    }

    suspend fun getTemperatureInsideState(): TemperatureInsideState {
        return withContext(Dispatchers.IO) {
            api.getTemperatureInsideState()
        }
    }

    suspend fun setTemperatureInsideState(state: TemperatureInsideState) {
        return withContext(Dispatchers.IO) {
            api.setTemperatureInsideState(state)
        }
    }

    suspend fun getMenuState(): MenuState {
        return withContext(Dispatchers.IO) {
            api.getMenuState()
        }
    }

    suspend fun getHistory(): History {
        return withContext(Dispatchers.IO) {
            api.getHistory()
        }
    }
    suspend fun setToken(state: TokenRequest) {
        return withContext(Dispatchers.IO) {
            api.setToken(state)
        }
    }
}
