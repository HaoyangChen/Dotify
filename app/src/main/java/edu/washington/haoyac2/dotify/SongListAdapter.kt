package edu.washington.haoyac2.dotify

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ericchee.songdataprovider.Song
import org.w3c.dom.Text

class SongListAdapter(initialListOfSong: List<Song>):RecyclerView.Adapter<SongListAdapter.SongViewHolder>() {
    private var listOfSongs: List<Song> = initialListOfSong.toList()
    var onSongClickListener: ((title: String, artist: String, song: Song) -> Unit)? = null
    var onSongLongClickListener: ((title: String, artist: String, pos: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)
        return SongViewHolder(view)
    }

    override fun getItemCount() = listOfSongs.size

    override fun onBindViewHolder(holder: SongViewHolder, position: Int): Unit {
        val song = listOfSongs[position]
        holder.bind(song, position)
    }

    fun change(newSong: List<Song>) {
        val callback = SongDiffCallback(listOfSongs, newSong)
        val diffResult = DiffUtil.calculateDiff(callback)
        diffResult.dispatchUpdatesTo(this)
        listOfSongs = newSong
    }

    fun updateRemoval(removedList: List<Song>) {
        this.listOfSongs = removedList
        notifyDataSetChanged()
    }


    inner class SongViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val tvSongTitle = itemView.findViewById<TextView>(R.id.tvSongTitle)
        private val artistName = itemView.findViewById<TextView>(R.id.artistName)
        private val ivSongImage = itemView.findViewById<ImageView>(R.id.ivSongImage)
        private val songTitleArtist = itemView.findViewById<TextView>(R.id.songTitleArtist)
        fun bind(song: Song, position: Int) {
            tvSongTitle.text = song.title
            artistName.text = song.artist
            ivSongImage.setImageResource(song.largeImageID)

            itemView.setOnClickListener {
                onSongClickListener?.invoke(song.title, song.artist, song)
            }
            itemView.setOnLongClickListener {
                onSongLongClickListener?.invoke(song.title, song.artist, position)
                notifyDataSetChanged()
                true
            }
        }
    }
}