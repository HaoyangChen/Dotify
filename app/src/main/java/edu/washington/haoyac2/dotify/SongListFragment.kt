package edu.washington.haoyac2.dotify

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_song_list.*

class SongListFragment: Fragment() {
    private var songListAdapter: SongListAdapter? = null
    private var listOfSongs: List<Song>? = null;
    private var onSongClickedListener: OnSongClickedListener? = null
    private lateinit var songManager: SongManager

    companion object {
        val TAG: String = SongListFragment::class.java.simpleName
        fun getInstance(): SongListFragment {
            return SongListFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        songManager = (context.applicationContext as DotifyApp).songManager

        if (context is OnSongClickedListener) {
            onSongClickedListener = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.listOfSongs = songManager.listOfSongs

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
        songListAdapter = listOfSongs?.let { SongListAdapter(it) }
        rvSongs.adapter = songListAdapter

        songListAdapter?.onSongClickListener = { song ->
            songManager.currentPlay = song
            onSongClickedListener?.onSongClicked(song)
        }
    }

    fun shuffleList() {
        songManager.shuffle()
        listOfSongs = songManager.listOfSongs
        songListAdapter?.shuffle(listOfSongs!!)
        rvSongs.scrollToPosition(0)
    }

    fun showSongList() {
        songListAdapter?.loadSong(songManager.listOfSongs)
    }
}

interface OnSongClickedListener {
    fun onSongClicked(song: Song)
}