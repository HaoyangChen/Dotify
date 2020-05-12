package edu.washington.haoyac2.dotify

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_song_list.*

class SongListFragment: Fragment() {
//    private lateinit var songListAdapter: SongListAdapter
    private var songListAdapter: SongListAdapter? = null
//    private lateinit var listOfSongs : MutableList<Song>
    private var listOfSongs: List<Song>? = null;
    private var onSongClickedListener: OnSongClickedListener? = null
    private lateinit var songManager: SongManager ////

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
//        if (savedInstanceState != null) {
//            with(savedInstanceState) {
//                listOfSongs = getParcelableArrayList<Song>(SONG_LIST_STATE) as MutableList<Song>
//            }
//        } else {
//            arguments?.let { args ->
//                val songList = args.getParcelableArrayList<Song>(ARG_SONG)
//                if (songList != null) {
//                    this.listOfSongs = songList
//                }
//            }
//        }
        return layoutInflater.inflate(R.layout.activity_song_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        songListAdapter = SongListAdapter(listOfSongs)
        songListAdapter = listOfSongs?.let { SongListAdapter(it) }
        rvSongs.adapter = songListAdapter

        songListAdapter?.onSongClickListener = { song ->
            songManager.currentPlay = song
            onSongClickedListener?.onSongClicked(song)
        }

    }

    fun shuffleList() {
//        val listToShuffle = listOfSongs?.map { it.copy() }?.toMutableList()
//
//        val newList = listToShuffle?.apply {
//            shuffle()
//        }
//        if (newList != null) {
//            songListAdapter?.shuffle(newList)
//            this.listOfSongs = newList.toMutableList()
//        }
//        rvSongs.scrollToPosition(0)

        songManager.shuffle()
        listOfSongs = songManager.listOfSongs
        songListAdapter?.shuffle(listOfSongs!!)
        rvSongs.scrollToPosition(0)
    }

    fun showSongList() {
        songListAdapter?.loadSong(songManager.listOfSongs)
    } /////

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putParcelableArrayList(SONG_LIST_STATE, listOfSongs as ArrayList<Song>)
//
//    }
}

interface OnSongClickedListener {
    fun onSongClicked(song: Song)
}