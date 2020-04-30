package edu.washington.haoyac2.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_ultimate_main.*

class UltimateMainActivity : AppCompatActivity(), OnSongClickedListener {

    private var currentSong: Song? = null
    companion object {
        private const val SELECTED_SONG = "SELECTED_SONG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ultimate_main)

        if (savedInstanceState != null){
            with(savedInstanceState) {
                currentSong = getParcelable(SELECTED_SONG)
                songTitleArtist.text = currentSong?.title.plus("-").plus(currentSong?.artist)
            }
        } else {
            val songListFragment = SongListFragment()
            var providedSongList = SongDataProvider.getAllSongs()
            val argumentBundle = Bundle().apply {
//                putParcelableArrayList(
//                    SongListFragment.ARG_SONG,
//                    providedSongList as ArrayList<Song>)
//                )
                var songList = providedSongList as ArrayList<Song>
                putParcelableArrayList(SongListFragment.ARG_SONG, songList)
            }
            songListFragment.arguments = argumentBundle
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragContainer, songListFragment)
                .commit()
        }

        supportFragmentManager.addOnBackStackChangedListener {
            val hasBackStack = supportFragmentManager.backStackEntryCount > 0
            if (hasBackStack) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                miniPlayer.visibility = View.VISIBLE //
            }
        }

        if (supportFragmentManager.backStackEntryCount > 0) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            miniPlayer.visibility = View.GONE
        }

        miniPlayer.setOnClickListener{
            miniPlayerClicked()
        }

        btnShuffle.setOnClickListener{
            getSongListFragment()?.shuffleList()
        }
    }

    private fun getSongListFragment() = supportFragmentManager.findFragmentById(R.id.fragContainer) as? SongListFragment
    private fun getSongDetailFragment() = supportFragmentManager.findFragmentByTag(NowPlayingFragment.TAG) as? NowPlayingFragment

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return super.onNavigateUp()
    }

    override fun onSongClicked(song: Song) {
        songTitleArtist.text = song.title.plus("-").plus(song.artist)
        currentSong = song
    }

    private fun miniPlayerClicked() {
        val immutableSong = this.currentSong
        if (immutableSong != null) {
            val immutableNowPlayingFragment = getSongDetailFragment()
            if(immutableNowPlayingFragment != null) {
                immutableNowPlayingFragment.updateSong(immutableSong)
            } else {
                val nowPlayingFragment = NowPlayingFragment()
                val currentSongBundle = Bundle().apply {
                    putParcelable(NowPlayingFragment.SONG_REF, immutableSong)
                }
                nowPlayingFragment.arguments = currentSongBundle
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                miniPlayer.visibility = View.INVISIBLE

                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragContainer, nowPlayingFragment)
                    .addToBackStack(NowPlayingFragment.TAG)
                    .commit()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(SELECTED_SONG, currentSong)
    }
}
