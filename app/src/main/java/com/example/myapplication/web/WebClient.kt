import com.example.myapplication.data.LightState
import com.example.myapplication.web.ApiService
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WebClient {


    val gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()


    val api = Retrofit.Builder()
        .baseUrl("https://ms.newtonbox.ru/smarthome3/")
        .addConverterFactory(GsonConverterFactory.create())
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


}
