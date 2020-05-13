package edu.washington.haoyac2.dotify

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_ultimate_main.*

class UltimateMainActivity : AppCompatActivity(), OnSongClickedListener {
    private var currentSong: Song? = null
    private var songListFragment: SongListFragment? = null
    private lateinit var songManager: SongManager
    private lateinit var apiManager: ApiManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ultimate_main)
        val songList = supportFragmentManager.findFragmentByTag(SongListFragment.TAG) as? SongListFragment
        songManager = (application as DotifyApp).songManager
        apiManager = (application as DotifyApp).apiManager

        if (songList == null) {
            apiManager.getSongs ({ songList ->
                songManager.listOfSongs = songList
                songListFragment?.showSongList()
                songListFragment = SongListFragment.getInstance()
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragContainer, songListFragment!!, SongListFragment.TAG)
                    .commit()
            },
                {
                    Toast.makeText(this, "error ", Toast.LENGTH_SHORT).show()
                })
        }


        this.currentSong = songManager.currentPlay
        if (currentSong != null) {
            songTitleArtist.text = currentSong?.title.plus(" - ").plus(currentSong?.artist)
        }


        supportFragmentManager.addOnBackStackChangedListener {
            val hasBackStack = supportFragmentManager.backStackEntryCount > 0
            if (hasBackStack) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                miniPlayer.visibility = View.VISIBLE
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
        miniPlayer.visibility = View.VISIBLE
        return super.onNavigateUp()
    }

    override fun onSongClicked(song: Song) {
        songTitleArtist.text = song.title.plus(" - ").plus(song.artist)
        currentSong = song
    }

    private fun miniPlayerClicked() {
        if (currentSong != null) {
            var nowPlayingFragment = getSongDetailFragment()
            if(nowPlayingFragment != null) {
                nowPlayingFragment.updateSong(currentSong!!)
            } else {
                nowPlayingFragment = NowPlayingFragment()
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragContainer, nowPlayingFragment, NowPlayingFragment.TAG)
                    .addToBackStack(NowPlayingFragment.TAG)
                    .commit()
            }
            miniPlayer.visibility = View.INVISIBLE
        }
    }

}