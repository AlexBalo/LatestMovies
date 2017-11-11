package com.balocco.movies.home.popular.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.balocco.movies.R
import com.balocco.movies.data.model.Movie
import com.balocco.movies.home.usecase.DateToHumanReadableUseCase
import java.util.*

class PopularAdapter(
        context: Context,
        private val dateToHumanReadableUseCase: DateToHumanReadableUseCase
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var movies: MutableList<Movie> = ArrayList()
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    var items: List<Movie>
        get() = movies
        set(newMovies) {
            if (!movies.isEmpty()) {
                movies.clear()
            }
            movies.addAll(newMovies)
            notifyDataSetChanged()
        }

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = 0

    override fun getItemCount() = movies.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = inflater.inflate(R.layout.listitem_movie, parent, false)
        return PopularItemViewHolder(view, dateToHumanReadableUseCase)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val itemViewHolder = viewHolder as PopularItemViewHolder
        itemViewHolder.onBindViewHolder(movies[position])
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder?) {
        super.onViewRecycled(holder)

        if (holder is PopularItemViewHolder) {
            holder.onViewRecycled()
        }
    }
}