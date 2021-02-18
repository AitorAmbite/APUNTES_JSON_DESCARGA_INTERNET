package com.cbellmont.ejemplodescargainternet

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


class GetAllFilms {
    companion object {
        suspend fun send(mainActivity : MainActivityInterface?) {

            val client = OkHttpClient() // creamos el cliente que nos permitira obtener datos de internet
            val url = "https://swapi.dev/api/films/" // declaramos la url a la que llamaremos
            // aqui creamos la request con el request builder , basicamente estamos creando lo que ejecutaremos para obtener datos de la api
            val request = Request.Builder()
                .url(url)
                .build()
            val call = client.newCall(request) // llamamos a la request con el cliente
            call.enqueue(object : Callback {
                // ejecutamos la peticion al servidor y en funcion de si falla o nos devuelve algo ejecutara una de las dos funciones

                //en caso de fallar ejecuta esta
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                    Log.e("GetAllFilms", call.toString())

                }
                //en caso de obtener respuesta ejecuta esta
                override fun onResponse(call: Call, response: Response) {
                    CoroutineScope(Dispatchers.IO).launch {
                        val bodyInString = response.body?.string()
                        bodyInString?.let {
                            Log.w("GetAllFilms", bodyInString)
                            val JsonObject = JSONObject(bodyInString)

                            val results = JsonObject.optJSONArray("results")
                            results?.let {
                                Log.w("GetAllFilms", results.toString())
                                val gson = Gson()

                                val itemType = object : TypeToken<List<Film>>() {}.type

                                val list = gson.fromJson<List<Film>>(results.toString(), itemType)

                                mainActivity?.onFilmsReceived(list)
                            }
                        }
                    }
                }
            })
        }
    }
}