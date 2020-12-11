package com.appforacademy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class List_RecyclerViewAdapter: RecyclerView.Adapter<List_RecyclerViewAdapter.EmptyViewHolder>() {

    public var moviesList = listOf<dataMovie>(dataMovie("Avengers: End Game",R.drawable.movie4,"13+","Action, Adventure, Drama",false,
                                                        4,"125 Reviews","137 min"),
                                                dataMovie("Tenet",R.drawable.movie5,"16+","Action, Sci-Fi, Thriller ",true,5,
                                                            "98 Reviews","97 min"),
                                                dataMovie("Black Widow",R.drawable.movie6,"13+","Action, Adventure, Sci-Fi",false,4,
                                                            "38 Reviews","102 min"),
                                                dataMovie("Wonder Woman 1984",R.drawable.movie7,"13+","Action, Adventure, Fantasy",false,
                                                            5,"74 Reviews","120 min"))

   // private val moviesList = listOf<String>("qqqqqqqqqqq","wwwwwwwwwww","eeeeeeeeeeeee","rrrrrrrrr")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmptyViewHolder {
        val itemView =LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie, parent, false)
        return EmptyViewHolder(itemView)
       // return EmptyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie, parent, false))

    }
    ////////////

    ////////////

    override fun onBindViewHolder(holder: EmptyViewHolder, position: Int) {


        holder.onBind(moviesList[position])
      //  holder.nameMovies.text=moviesList[position].NameMovies.toString()
       // holder.nameMovies.text="wwwwwwww"

    }
   // override fun getItemCount(): Int = 4
    override fun getItemCount(): Int {
        return moviesList.count()
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
    var like1 = itemView.findViewById<ImageView>(R.id.ic_appraisal1_list)
    var like2 = itemView.findViewById<ImageView>(R.id.ic_appraisal2_list)
    var like3 = itemView.findViewById<ImageView>(R.id.ic_appraisal3_list)
    var like4 = itemView.findViewById<ImageView>(R.id.ic_appraisal4_list)
    var like5 = itemView.findViewById<ImageView>(R.id.ic_appraisal5_list)
    var reviews = itemView.findViewById<TextView>(R.id.tv_countFeadBack_list)
    var time = itemView.findViewById<TextView>(R.id.tv_movie_time)
    var ic_movies = itemView.findViewById<ImageView>(R.id.ic_afisha_movies)

     fun onBind(moviesList: dataMovie) {

         nameMovies.text=moviesList.NameMovies
         age.text=moviesList.Age
         genreMovie.text=moviesList.Genre
         if (moviesList.Favorit==true){
             favoritIc.setImageResource(R.drawable.like_true)
         }else{
             favoritIc.setImageResource(R.drawable.like)
         }
         if (moviesList.Like==5){
             like1.setImageResource(R.drawable.staricon1)
             like2.setImageResource(R.drawable.staricon1)
             like3.setImageResource(R.drawable.staricon1)
             like4.setImageResource(R.drawable.staricon1)
             like5.setImageResource(R.drawable.staricon1)
         }
         if (moviesList.Like==4){
             like1.setImageResource(R.drawable.staricon1)
             like2.setImageResource(R.drawable.staricon1)
             like3.setImageResource(R.drawable.staricon1)
             like4.setImageResource(R.drawable.staricon1)
             like5.setImageResource(R.drawable.staricon)
         }
         if (moviesList.Like==3){
             like1.setImageResource(R.drawable.staricon1)
             like2.setImageResource(R.drawable.staricon1)
             like3.setImageResource(R.drawable.staricon1)
             like4.setImageResource(R.drawable.staricon)
             like5.setImageResource(R.drawable.staricon)
         }
         if (moviesList.Like==2){
             like1.setImageResource(R.drawable.staricon1)
             like2.setImageResource(R.drawable.staricon1)
             like3.setImageResource(R.drawable.staricon)
             like4.setImageResource(R.drawable.staricon)
             like5.setImageResource(R.drawable.staricon)
         }
         if (moviesList.Like==1){
             like1.setImageResource(R.drawable.staricon1)
             like2.setImageResource(R.drawable.staricon)
             like3.setImageResource(R.drawable.staricon)
             like4.setImageResource(R.drawable.staricon)
             like5.setImageResource(R.drawable.staricon)
         }
         if (moviesList.Like==0){
             like1.setImageResource(R.drawable.staricon)
             like2.setImageResource(R.drawable.staricon)
             like3.setImageResource(R.drawable.staricon)
             like4.setImageResource(R.drawable.staricon)
             like5.setImageResource(R.drawable.staricon)
         }
         reviews.text=moviesList.Reviews
         time.text=moviesList.Time
         ic_movies.setImageResource(moviesList.IC_movies)


     }
}


    //////////////////////////
}

