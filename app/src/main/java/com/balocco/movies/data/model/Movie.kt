package com.balocco.movies.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.util.*

data class Movie(@SerializedName("id") val id: String = "",
                 @SerializedName("poster_path") val posterPath: String = "",
                 @SerializedName("adult") val adult: Boolean = false,
                 @SerializedName("overview") val overview: String = "",
                 @SerializedName("release_date") val releaseDate: Date,
                 @SerializedName("genre_ids") val genres: List<Int> = emptyList(),
                 @SerializedName("original_title") val originalTitle: String = "",
                 @SerializedName("original_language") val originalLanguage: String = "",
                 @SerializedName("title") val title: String = "",
                 @SerializedName("backdrop_path") val backdropPath: String = "",
                 @SerializedName("popularity") val popularity: Double = 0.0,
                 @SerializedName("vote_count") val voteCount: Int = 0,
                 @SerializedName("video") val video: Boolean = false,
                 @SerializedName("vote_average") val voteAverage: Double = 0.0
) : Parcelable {
    constructor(source: Parcel) : this(
            id = source.readString(),
            posterPath = source.readString(),
            adult = 1 == source.readInt(),
            overview = source.readString(),
            releaseDate = source.readSerializable() as Date,
            genres = ArrayList<Int>().apply { source.readList(this, Int::class.java.classLoader) },
            originalTitle = source.readString(),
            originalLanguage = source.readString(),
            title = source.readString(),
            backdropPath = source.readString(),
            popularity = source.readDouble(),
            voteCount = source.readInt(),
            video = 1 == source.readInt(),
            voteAverage = source.readDouble()
    )

    companion object {
        val CREATOR: Parcelable.Creator<Movie> = object : Parcelable.Creator<Movie> {
            override fun createFromParcel(source: Parcel): Movie = Movie(source)
            override fun newArray(size: Int): Array<Movie?> = arrayOfNulls(size)
        }
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(posterPath)
        writeInt((if (adult) 1 else 0))
        writeString(overview)
        writeSerializable(releaseDate)
        writeList(genres)
        writeString(originalTitle)
        writeString(originalLanguage)
        writeString(title)
        writeString(backdropPath)
        writeDouble(popularity)
        writeInt(voteCount)
        writeInt((if (video) 1 else 0))
        writeDouble(voteAverage)
    }
}