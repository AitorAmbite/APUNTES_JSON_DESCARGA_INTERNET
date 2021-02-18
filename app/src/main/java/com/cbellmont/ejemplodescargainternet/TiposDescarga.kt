package com.cbellmont.ejemplodescargainternet

class TiposDescarga {

    /*
    * Podemos usar la forma "tradicional" con los metodos onResponse onFail
    *
    * Con la que necesitariamos usar un callback para devolver los datos a main por ejemplo
    *
    * y la opcion de call.execute() que esto ejecuta la llamada a la api y no pasa de ahi hasta que no obtiene una respuesta.
    * permitiendonos hacer un return de call.execute sin necesidad de usar callbacks
    * */
    // ejemplo.
    suspend fun downloadSpecificCoin(coinId: String):CoinSpecific?{
        val client = OkHttpClient()
        val url = "https://api.coinpaprika.com/v1/coins/$coinId"
        val request = Request.Builder()
            .url(url)
            .build()
        val call = client.newCall(request)
        val gson = Gson()
        val itemType = object : TypeToken<CoinSpecific>(){}.type

        return gson.fromJson(call.execute().body?.string(),itemType)
    }
}