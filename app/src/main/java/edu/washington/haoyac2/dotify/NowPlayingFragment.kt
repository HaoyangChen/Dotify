package edu.washington.haoyac2.dotify

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_playing_now.*

class NowPlayingFragment : Fragment() {
    private var showApplyBtn = false
    private lateinit var dotify: DotifyApp
    private var currentSong: Song? = null

    companion object {
        val TAG: String = NowPlayingFragment::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dotify = context?.applicationContext as DotifyApp
        currentSong = dotify.songManager.currentPlay
    }

    fun updateSong(song: Song) {
        this.currentSong = song
        Picasso.get().load(song.largeImageURL).into(songImage)
        songTitle.text = song.title
        artist.text = song.artist
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_playing_now, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateSongViews()
    }

    private fun updateSongViews() {
        currentSong?.let {
                songTitle.text = it.title
                artist.text = it.artist
                Picasso.get().load(it.largeImageURL).into(songImage)
                songNumber.text = dotify.playCounter.toString().plus(" play(s)")
        }

        previousBtn.setOnClickListener{
            Toast.makeText(context, "Skipping to previous track", Toast.LENGTH_SHORT).show()
        }
        nextBtn.setOnClickListener{
            Toast.makeText(context, "Skipping to next track", Toast.LENGTH_SHORT).show()
        }

        musicPlayBtn.setOnClickListener{
            dotify.playCounter = dotify.playCounter.plus(1)
            songNumber.text = "${dotify.playCounter} play(s)"
        }

        btnChangeUser.setOnClickListener{
            btnChangeUserClicked()
        }
    }

    private fun btnChangeUserClicked() {
        if (!showApplyBtn) {
            btnChangeUser.text = "Apply"
            showApplyBtn = true
            userNameInput.setText(userName.text)
            userName.visibility = View.GONE
            userNameInput.visibility = View.VISIBLE
        } else {
            if (userNameInput.text.toString().isNotEmpty()) {
                btnChangeUser.text = "CHANGE USER"
                showApplyBtn = false
                userName.setText(userNameInput.text)
                userNameInput.visibility = View.GONE
                userName.visibility = View.VISIBLE
            } else {
                Toast.makeText(context, "Username may not be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
