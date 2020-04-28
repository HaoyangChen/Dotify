package edu.washington.haoyac2.dotify

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import kotlinx.android.synthetic.main.activity_song_list.*

class SongListFragment: Fragment() {
    private var listOfSongs = mutableListOf<Song>()
    private var onSongClickListener: OnSongClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnSongClickListener) {
            onSongClickListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.activity_song_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        var listOfSongs = mutableListOf<Song>()
        listOfSongs.addAll(SongDataProvider.getAllSongs())
        val songAdapter = SongListAdapter(listOfSongs)
        var currentPlay: Song? = null
        rvSongs.adapter = songAdapter

        songAdapter.onSongClickListener = { title, artist, song ->
            songTitleArtist.text = title.plus(" - ").plus(artist)
            currentPlay = song;
            onSongClickListener?.onSongClicked(song)
        }

        songAdapter.onSongLongClickListener = { title, artist, pos ->
            listOfSongs.removeAt(pos)
            songAdapter.updateRemoval(listOfSongs)
            Toast.makeText(context, "$title by $artist deleted", Toast.LENGTH_SHORT).show()
        }

        btnShuffle.setOnClickListener{
//            val listToShuffle = listOfSongs.map{ it.copy() }.toMutableList()
//            val shuffledList = listToShuffle.apply{
//                shuffle()
//            }
//            listOfSongs = shuffledList
//            songAdapter.change(shuffledList)
//            rvSongs.scrollToPosition(0)
            shuffleList(songAdapter)
        }


//        miniPlayer.setOnClickListener{
//            startActivityForResult(
//                Intent(context, ComposeActivity::class.java),
//                ListEmailsActivity.COMPOSE_REQUEST_CODE
//            )
//        }

    }

    fun shuffleList(songAdapter: SongListAdapter) {
        val listToShuffle = listOfSongs.map{ it.copy() }.toMutableList()
        val shuffledList = listToShuffle.apply{
            shuffle()
        }
        listOfSongs = shuffledList
        songAdapter.change(shuffledList)
        rvSongs.scrollToPosition(0)
    }
}

interface OnSongClickListener {
    fun onSongClicked(song: Song)
}