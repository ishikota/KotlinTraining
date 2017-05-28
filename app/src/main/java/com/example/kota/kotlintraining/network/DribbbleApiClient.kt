package com.example.kota.kotlintraining.network

import com.example.kota.kotlintraining.network.entity.Shots
import com.google.gson.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type


object DribbbleApiClient: DribbbleRxService {

    val client by lazy {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    val gson by lazy {
        GsonBuilder()
                .registerTypeAdapter(Shots::class.java, ShotsDeserializer())
                .create()
    }

    val service: DribbbleRxService = Retrofit.Builder()
            .baseUrl(API_END_POINT)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(DribbbleRxService::class.java)


    override fun getShots(page: Int, per_page: Int): Observable<Shots> {
        return service.getShots(page, per_page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

}

class ShotsDeserializer : JsonDeserializer<Shots> {

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Shots {
        val array = json.asJsonArray
        val jo = JSONObject()
        try {
            jo.put("items", array)
        } catch (e: JSONException) {
            throw JsonParseException(e.message)
        }

        val wrapped_json = "{\"items\":$array}"
        return Gson().fromJson(wrapped_json, Shots::class.java)
    }
}