package com.victor.dogbreeds.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.victor.dogbreeds.BuildConfig
import com.victor.dogbreeds.business.dataClasses.AllBreedsVO
import com.victor.dogbreeds.business.dataClasses.BreedsVO
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModules {
    private fun createRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private fun createClient(): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    private fun createGson(): Gson {
        return GsonBuilder()

            .registerTypeAdapter(
                AllBreedsVO::class.java,
                allBreedsDeserializer
            )

            .create()
    }

    val instance = module {
        factory { createClient() }
        factory { createGson() }

        single { createRetrofit(get(), get()) }
    }

    val allBreedsDeserializer = JsonDeserializer<AllBreedsVO> { json, _, _ ->
        val jsonMessage = json.asJsonObject.getAsJsonObject("message")
        val jsonStatus = json.asJsonObject.getAsJsonPrimitive("status")
        val masterBreeds = jsonMessage.keySet()

        val message = masterBreeds.map { breed ->
            BreedsVO(
                breedName = breed.toString(),
                subBreeds = jsonMessage.get(breed).asJsonArray.map { subBreed ->
                    subBreed.asString
                }
            )
        }

        AllBreedsVO(message, jsonStatus.asString)
    }
}