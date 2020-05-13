package edu.washington.haoyac2.dotify

import android.app.Application

class DotifyApp: Application() {
    var playCounter: Int = 0
    var userName: String = "Eric Chen"
    lateinit var apiManager: ApiManager
    lateinit var songManager: SongManager

    override fun onCreate() {
        super.onCreate()
        apiManager = ApiManager(this)
        songManager = SongManager()
    }
}