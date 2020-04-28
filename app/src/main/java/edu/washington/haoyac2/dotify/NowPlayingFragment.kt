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
    private var song: Song? = null
    private var randomPlayNumber = Random.nextInt(1000, 50000)
    private var showApplyBtn = false

    companion object {
        val TAG: String = NowPlayingFragment::class.java.simpleName
        const val ARG_SONG = "arg_song"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { args ->
            val song = args.getParcelable<Song>(ARG_SONG)
            if (song != null) {
                this.song = song
            }
        }
    }

    fun updateSong(song: Song) {
        this.song = song
        updateSongViews()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_playing_now, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateSongViews()
    }

    private fun updateSongViews() {
        song?.let {
            songImage.setImageResource(it.largeImageID)
            songTitle.text = it.title
            artist.text = it.artist
        }
        songNumber.text = "$randomPlayNumber plays"
        Log.i("songNumber", "started")
        previousBtn.setOnClickListener{
            Toast.makeText(context, "Skipping to previous track", Toast.LENGTH_SHORT).show()
        }
        nextBtn.setOnClickListener{
            Toast.makeText(context, "Skipping to next track", Toast.LENGTH_SHORT).show()
        }
        songImage.setOnLongClickListener{
//            userName.setTextColor(getMyColor(R.color.lightBlue))
//            songTitle.setTextColor(getMyColor(R.color.lightBlue))
//            artist.setTextColor(getMyColor(R.color.lightBlue))
//            songNumber.setTextColor(getMyColor(R.color.lightBlue))
            true
        }

        musicPlayBtn.setOnClickListener{
            Log.i("eric2", "clicked")
            randomPlayNumber += 1
            songNumber.text = "$randomPlayNumber plays"
        }

        btnChangeUser.setOnClickListener{
            btnChangeUserClicked()
        }
    }

    private fun btnChangeUserClicked() {
        if (!showApplyBtn) {
            Log.i("eric3", "show")
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
