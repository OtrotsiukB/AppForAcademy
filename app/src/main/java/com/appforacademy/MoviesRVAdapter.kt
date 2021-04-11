package com.appforacademy

//import android.R

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.academy.fundamentals.homework.features.data.Movie
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners


class MoviesRVAdapter(private val listener: OnItemClickListener): RecyclerView.Adapter<MoviesRVAdapter.EmptyViewHolder>() {

    private var data = listOf<Movie>()


    interface OnItemClickListener {
        fun onItemClick(data: Movie)
    }

    fun setData(data: List<Movie>) {
        this.data = data
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmptyViewHolder {

        val itemView =LayoutInflater.from(parent.context).inflate(
            R.layout.movie_holder,
            parent,
            false
        )
        return EmptyViewHolder(itemView)
       // return EmptyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie, parent, false))

    }
    ////////////

    ////////////

    override fun onBindViewHolder(holder: EmptyViewHolder, position: Int) {


        holder.onBind(data[position],listener)
      //  holder.nameMovies.text=moviesList[position].NameMovies.toString()
       // holder.nameMovies.text="wwwwwwww"

    }
   // override fun getItemCount(): Int = 4
    override fun getItemCount(): Int {
        return data.count()
    }
    override fun getItemViewType(position: Int): Int {

        return 0
    }
//////////////////////////
class EmptyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    var nameMovies = itemView.findViewById<TextView>(R.id.tv_name_movies)
    var age = itemView.findViewById<TextView>(R.id.tv_age)
    var genreMovie = itemView.findViewById<TextView>(R.id.tv_typeMovie)
    var favoritIc = itemView.findViewById<ImageView>(R.id.ic_like)

    var reviews = itemView.findViewById<TextView>(R.id.tv_countFeadBack_list)
    var time = itemView.findViewById<TextView>(R.id.tv_movie_time)
    var ic_movies = itemView.findViewById<ImageView>(R.id.ic_afisha_movies)
    var ratingBar= itemView.findViewById<RatingBar>(R.id.ratingBarOnHolderMovie)

     fun onBind(data: Movie, listener: OnItemClickListener) {

         nameMovies.text=data.title
         age.text=data.minimumAge.toString()+"+"
         genreMovie.text=data.genres.joinToString(separator = ", ", transform = { it.name })
         ratingBar.rating=data.ratings


         reviews.text=data.numberOfRatings.toString()+" Reviews"
         time.text=data.runtime.toString()+" min"



         Glide.with(itemView.context)
             //.load(R.drawable.movie6)
              .load(data.poster)

              .into(ic_movies)


        // ic_movies.setBackgroundResource(R.drawable.gradient)*/
         itemView.setOnClickListener { listener.onItemClick(data) }

     }
}




    //////////////////////////
}

