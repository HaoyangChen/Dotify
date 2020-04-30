package edu.washington.haoyac2.dotify

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ericchee.songdataprovider.Song
import kotlinx.android.synthetic.main.fragment_playing_now.*
import kotlin.random.Random

/**
 * A simple [Fragment] subclass.
 */
class NowPlayingFragment : Fragment() {
    private var randomPlayNumber = Random.nextInt(1000, 50000000)
    private var showApplyBtn = false

    companion object {
        val TAG: String = NowPlayingFragment::class.java.simpleName
        const val SONG_REF = "song_ref"
        const val PLAY_COUNT = "play_count"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            with(savedInstanceState) {
                randomPlayNumber = getInt(PLAY_COUNT, Random.nextInt(1000, 50000000))
            }
        } else {
            randomPlayNumber = Random.nextInt(1000, 50000000)
        }
    }

    fun updateSong(song: Song) {
        songImage.setImageResource(song.largeImageID)
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
        arguments?.let { args ->
            val song = args.getParcelable<Song>(SONG_REF)
            if (song != null) {
                songTitle.text = song.title
                artist.text = song.artist
                songImage.setImageResource(song.largeImageID)
            }
        }

        songNumber.text = "$randomPlayNumber plays"
        previousBtn.setOnClickListener{
            Toast.makeText(context, "Skipping to previous track", Toast.LENGTH_SHORT).show()
        }
        nextBtn.setOnClickListener{
            Toast.makeText(context, "Skipping to next track", Toast.LENGTH_SHORT).show()
        }

        musicPlayBtn.setOnClickListener{
            randomPlayNumber += 1
            songNumber.text = "$randomPlayNumber plays"
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

    override fun onSaveInstanceState(outState: Bundle) {
        randomPlayNumber?.let { outState.putInt(PLAY_COUNT, it) }
        super.onSaveInstanceState(outState)
//        outState.run {
//            putInt(PLAY_COUNT, randomPlayNumber)
//        }
    }

}
