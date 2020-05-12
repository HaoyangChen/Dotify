package edu.washington.haoyac2.dotify

class SongManager {
    var listOfSongs: List<Song>
    var currentPlay: Song? = null

    init {
        listOfSongs = emptyList()
    }

    fun shuffle() {
        listOfSongs = listOfSongs.toMutableList().apply {
            shuffle()
        }.toList()
    }
}