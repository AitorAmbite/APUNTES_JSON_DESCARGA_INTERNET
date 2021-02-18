package com.cbellmont.ejemplodescargainternet

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class TransformarJson_TO_gson {

    fun transformar() {
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
            }

        }
    }
    /*
    * Para transformar un json a gson
    *
    * creamos una variable que va a almacenar el objeto o el array de json
    *
    * objecto -> JSONObject(string JSON)
    *
    * array -> JSONArray(string JSON)
    *
    * val results = JsonObject.optJSONArray("nombreObjeto") <- esto sirve para coger lo que hay dentro de un campo
    *
    * creamos una variable gson = Gson() que sera la encargada de transformar nuestro json
    *
    * val itemType = object : TypeToken<List<Film>>() {}.type -> declaramos el tipo que se usara. en caso de ser una lista (devuelva mas de 1 resultado) usamos list<Tipo> y si no <TIPO>
    *
    * gson.fromJson<List<TIPO>>(json.toString(),itemType) -> ponemos<List<Tipo>> antes del json si son varios resultados.
    * si no simplemente fromJson
    * */
}