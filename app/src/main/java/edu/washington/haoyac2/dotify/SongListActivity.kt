//package edu.washington.haoyac2.dotify
//
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.widget.Toast
//import com.ericchee.songdataprovider.Song
//import com.ericchee.songdataprovider.SongDataProvider
//import edu.washington.haoyac2.dotify.MainActivity.Companion.SONG_KEY
//import kotlinx.android.synthetic.main.activity_song_list.*
//
//class SongListActivity : AppCompatActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_song_list)
//        title = "All Songs"
//        var listOfSongs = mutableListOf<Song>()
//        listOfSongs.addAll(SongDataProvider.getAllSongs())
//        val songAdapter = SongListAdapter(listOfSongs)
//        var currentPlay: Song? = null
//
//        songAdapter.onSongClickListener = { title, artist, song ->
//            songTitleArtist.text = title.plus(" - ").plus(artist)
//            currentPlay = song;
//        }
//
//        songAdapter.onSongLongClickListener = { title, artist, pos ->
//            listOfSongs.removeAt(pos)
//            songAdapter.updateRemoval(listOfSongs)
//            Toast.makeText(this, "$title by $artist deleted", Toast.LENGTH_SHORT).show()
//        }
//
//        miniPlayer.setOnClickListener{
//            if (currentPlay != null) {
//                val intent = Intent(this, MainActivity::class.java)
//                intent.putExtra(SONG_KEY, currentPlay)
//                startActivity(intent)
//            }
//        }
//
//        btnShuffle.setOnClickListener{
//            val listToShuffle = listOfSongs.map{ it.copy() }.toMutableList()
//            val shuffledList = listToShuffle.apply{
//                shuffle()
//            }
//            listOfSongs = shuffledList
//            songAdapter.change(shuffledList)
//            rvSongs.scrollToPosition(0)
//        }
//        rvSongs.adapter = songAdapter
//    }
//}
