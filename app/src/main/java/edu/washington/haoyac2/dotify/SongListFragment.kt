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
    private lateinit var songListAdapter: SongListAdapter
    private lateinit var listOfSongs : MutableList<Song>
//    private var songListAdapter: SongListAdapter? = null
//   private var listOfSongs: Array<Song>? = null;
    private var onSongClickedListener: OnSongClickedListener? = null

    companion object {
        val TAG: String = SongListFragment::class.java.simpleName
        const val SONG_LIST_STATE = "song_list_state"
        const val ARG_SONG = "arg_song"
//        private const val LIST = "list"
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        if (savedInstanceState == null) {
//            arguments?.let { args ->
//                val songList = args.getParcelableArray(ARG_SONG)
//                if (songList != null) {
//                    this.listOfSongs = songList as Array<Song>
//                }
//            }
//        } else {
//            with(savedInstanceState) {
//                listOfSongs = getParcelableArray(SONG_LIST_STATE) as Array<Song>?
//            }
//        }
//    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnSongClickedListener) {
            onSongClickedListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (savedInstanceState != null) {
            with(savedInstanceState) {
                listOfSongs = getParcelableArrayList<Song>(SONG_LIST_STATE) as MutableList<Song>
            }
        } else {
            arguments?.let { args ->
//                listOfSongs =
//                    args.getParcelableArrayList<Song>(ARG_SONG) as MutableList<Song>
                val songList = args.getParcelableArrayList<Song>(ARG_SONG)
                if (songList != null) {
                    this.listOfSongs = songList as MutableList<Song>
                }
            }
        }
        return layoutInflater.inflate(R.layout.activity_song_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // val songAdapter = SongListAdapter(listOfSongs)
//        songListAdapter = listOfSongs?.toList()?.let { SongListAdapter(it as MutableList<Song>) }
//        rvSongs.adapter = songListAdapter

        songListAdapter = SongListAdapter(listOfSongs)
        rvSongs.adapter = songListAdapter

        songListAdapter?.onSongClickListener = { song ->
            onSongClickedListener?.onSongClicked(song)
        }

    }

    fun shuffleList() {
        listOfSongs.shuffle()
        val songListToShuffle: MutableList<Song> = listOfSongs
        songListAdapter.shuffle(songListToShuffle)

    }

//    fun shuffleList() {
//        val shuffleList = listOfSongs?.map { it.copy() }?.toMutableList()
//
//        val newList = shuffleList?.apply {
//            shuffle()
//        }
//
//        if (newList != null) {
//            songListAdapter?.shuffle(newList)
//            this.listOfSongs = newList.toTypedArray()
//        }
//    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(SONG_LIST_STATE, listOfSongs as ArrayList<Song>)

    }
}

interface OnSongClickedListener {
    fun onSongClicked(song: Song)
}