package com.kentin.chinesefood.model


import okhttp3.OkHttpClient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Request
import okhttp3.Response
import java.io.InputStreamReader


fun main() {
    val chineseFood = ChineseFoodAPI.loadChineseFood()
    println(chineseFood)
}

object ChineseFoodAPI {
    val client = OkHttpClient()
    val gson = Gson()

    const val URL_API = "https://chinese-food-db.p.rapidapi.com/"

    fun loadChineseFood(): List<ChineseFood> {
        val json: String = sendGet(URL_API)
        return gson.fromJson(json, Array<ChineseFood>::class.java).toList()
    }

    fun sendGet(url: String) : String {
        println("url: $url")
        val request = Request.Builder()
            .url(url)
            .addHeader("X-RapidAPI-Key", "803ec62168msh975d0a90da37a71p135bf8jsn5a7a23662365")
            .addHeader("X-RapidAPI-Host", "chinese-food-db.p.rapidapi.com")
            .build()
        return client.newCall(request).execute().use {
            if (!it.isSuccessful) {
                throw Exception("Réponse du serveur incorrect :${it.code}\n${it.body.string()}")
            }
            it.body.string()
        }
    }
}

data class PictureFood(
    val id: Int,
    val title: String,
    val difficulty: String,
    val image: String,
)
data class ChineseFood(
    val id: Int,
    val title: String,
    val difficulty: String,
    val image: String,
)
data class ChineseFoodDetail(
    val id: Int,
    val title: String,
    val difficulty: String,
    val portion: String,
    val time: String,
    val description : String,
    val image: String,
)