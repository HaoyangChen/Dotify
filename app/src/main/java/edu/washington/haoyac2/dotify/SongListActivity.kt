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
        val listOfSongs = mutableListOf<Song>()
        listOfSongs.addAll(SongDataProvider.getAllSongs())
//        val listOfSongs: List<Song> = SongDataProvider.getAllSongs()
        val songAdapter = SongListAdapter(listOfSongs)
        rvSongs.adapter = songAdapter

        btnShuffle.setOnClickListener{
            val newSong = listOfSongs.apply{
                shuffle()
            }
            songAdapter.change(newSong)
        }
    }
}
