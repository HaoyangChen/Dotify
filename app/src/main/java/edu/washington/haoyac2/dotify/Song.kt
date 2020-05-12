package edu.washington.haoyac2.dotify

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Song(
    val id: String,
    val title: String,
    val artist: String,
    val durationMillis: Long,
    val smallImageURL: String,
    val largeImageURL: String
): Parcelable