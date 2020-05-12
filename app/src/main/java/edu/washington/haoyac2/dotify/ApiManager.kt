package edu.washington.haoyac2.dotify

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson

class ApiManager(context: Context) {

    private val queue: RequestQueue = Volley.newRequestQueue(context)

    fun getSongs(onSongsReady: (List<Song>) -> Unit, onError: (() -> Unit)? = null) {
        val musicURL = "https://raw.githubusercontent.com/echeeUW/codesnippets/master/musiclibrary.json"

        val request = StringRequest(
            Request.Method.GET, musicURL,
            {response ->
                val gson = Gson()
                val data = gson.fromJson(response, AllSongs::class.java)

                onSongsReady(data.songs)
            },
            {
                onError?.invoke()
                Log.i("error", it.toString())
            }
        )

        request.setShouldCache(false)

        queue.add(request)
    }
}