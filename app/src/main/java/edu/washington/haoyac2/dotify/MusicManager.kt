package edu.washington.haoyac2.dotify

import android.content.Context
import android.widget.Toast

class MusicManager {
    var listOfSongs: List<Song>
    var currentPlay: Song? = null
    init {
        listOfSongs = emptyList()
    }

    fun nextSong(context: Context): Song? {
        if (listOfSongs.size == 1) {
            Toast.makeText(context, "Only one song in the list", Toast.LENGTH_SHORT).show()
            return currentPlay
        }
        var nextSongIndex = listOfSongs.indexOf(currentPlay) + 1
        if (nextSongIndex >= listOfSongs.size) {
            nextSongIndex = 0
        }
        val nextSong = listOfSongs[nextSongIndex]
        currentPlay = nextSong
        Toast.makeText(context, "Skipping to next track", Toast.LENGTH_SHORT).show()
        return nextSong
    }

    fun prevSong(context: Context): Song? {
        if (listOfSongs.size == 1) {
            Toast.makeText(context, "Only one song in the list", Toast.LENGTH_SHORT).show()
            return currentPlay
        }
        var prevSongIndex = listOfSongs.indexOf(currentPlay) - 1
        if (prevSongIndex < 0) {
            prevSongIndex = listOfSongs.size - 1
        }
        val prevSong = listOfSongs[prevSongIndex]
        currentPlay = prevSong
        Toast.makeText(context, "Skipping to previous track", Toast.LENGTH_SHORT).show()
        return prevSong
    }

    fun shuffle() {
        listOfSongs = listOfSongs.toMutableList().apply {
            shuffle()
        }.toList()
    }
}