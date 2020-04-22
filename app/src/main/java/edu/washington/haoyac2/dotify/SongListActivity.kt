package edu.washington.haoyac2.dotify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import kotlinx.android.synthetic.main.activity_song_list.*

class SongListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)
        var listOfSongs = mutableListOf<Song>()
        listOfSongs.addAll(SongDataProvider.getAllSongs())
//        val listOfSongs: List<Song> = SongDataProvider.getAllSongs()
        val songAdapter = SongListAdapter(listOfSongs)
        var currentPlay: Song? = null

        songAdapter.onSongClickListener = { title, artist, song ->
            songTitleArtist.text = title.plus(" - ").plus(artist)
            currentPlay = song;
        }


        btnShuffle.setOnClickListener{
//            val songAdapter = SongListAdapter(listOfSongs)
//            rvSongs.adapter = songAdapter
            val listToShuffle = listOfSongs.map{ it.copy() }.toMutableList()
            val shuffledList = listToShuffle.apply{
                shuffle()
            }
            listOfSongs = shuffledList
            songAdapter.change(shuffledList)
        }
        rvSongs.adapter = songAdapter
    }
}
