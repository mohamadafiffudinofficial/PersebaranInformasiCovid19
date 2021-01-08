package retrofit

import com.example.newapp.MainModel
import retrofit2.Call
import retrofit2.http.*

interface ApiEndpoint {

    @GET("indonesia")
    fun getData(): Call<List<MainModel>>
}