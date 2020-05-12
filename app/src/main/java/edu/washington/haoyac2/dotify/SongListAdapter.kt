package edu.washington.haoyac2.dotify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class SongListAdapter(initialListOfSong: List<Song>):RecyclerView.Adapter<SongListAdapter.SongViewHolder>() {
    private var listOfSongs: List<Song> = initialListOfSong.toList()
    var onSongClickListener: ((song: Song) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)
        return SongViewHolder(view)
    }

    override fun getItemCount() = listOfSongs.size

    override fun onBindViewHolder(holder: SongViewHolder, position: Int): Unit {
        holder.bind(listOfSongs[position])
    }

    fun shuffle(newList: List<Song>) {
        val callback = SongDiffCallback(this.listOfSongs, newList)
        val diffResult = DiffUtil.calculateDiff(callback)
        diffResult.dispatchUpdatesTo(this)

        this.listOfSongs = newList
    }

    fun loadSong(listToLoad: List<Song>) {
        this.listOfSongs = listToLoad
        notifyDataSetChanged()
    }

    inner class SongViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val tvSongTitle = itemView.findViewById<TextView>(R.id.tvSongTitle)
        private val artistName = itemView.findViewById<TextView>(R.id.artistName)
        private val ivSongImage = itemView.findViewById<ImageView>(R.id.ivSongImage)
        fun bind(song: Song) {
            tvSongTitle.text = song.title
            artistName.text = song.artist
            Picasso.get().load(song.smallImageURL).into(ivSongImage)

            itemView.setOnClickListener {
                onSongClickListener?.invoke(song)
            }
        }
    }
}