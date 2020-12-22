package com.appforacademy

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.academy.fundamentals.homework.features.data.Movie
import com.bumptech.glide.Glide

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_MOVIE = "param1"


/**
 * A simple [Fragment] subclass.
 * Use the [fragment_movies_details.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragment_movies_details : Fragment() {
    // TODO: Rename and change types of parameters
    private var movieData:Movie?=null

    private var listener: ClickListenerDetall?=null
    private var back:TextView?=null
    private var recycler: RecyclerView? = null

    private var backPicMovie:ImageView?= null
    private var nameMovie:TextView?=null
    private var genre:TextView?=null
    private var ageCan:TextView?=null
    private var reviews:TextView?=null
    private var storyLine:TextView?=null
    private var ratingBar:RatingBar?=null
    private val actorAdapter = ActorRVAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieData = it.getParcelable(ARG_MOVIE)

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_details, container, false)
    }

    private fun addInfoMovieDetal(){
        Glide.with(this)
            .load(movieData?.backdrop)
            .into(backPicMovie!!)
        nameMovie?.text=movieData?.title
        genre?.text=movieData?.genres?.joinToString(separator = ", ", transform = { it.name })
        ageCan?.text=movieData?.minimumAge.toString()+"+"
        reviews?.text=movieData?.numberOfRatings.toString()+" Reviews"
        storyLine?.text=movieData?.overview
        ratingBar?.rating= movieData?.ratings!!
    }


    private fun openActorAdapter(){
        actorAdapter.setData(movieData!!.actors)
        recycler?.adapter=actorAdapter
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backPicMovie=view.findViewById(R.id.ic_backMovies)
        nameMovie=view.findViewById(R.id.nameMovie)
        genre=view.findViewById(R.id.tv_typeMovie)
        ageCan=view.findViewById(R.id.canAge)
        reviews=view.findViewById(R.id.countFeadBack)
        storyLine=view.findViewById(R.id.Storylinetext)
        ratingBar=view.findViewById(R.id.ratingBarDetall)

        recycler = view.findViewById(R.id.rv_actors)

        //recycler?.adapter=ActorRVAdapter()

        back=view.findViewById<TextView>(R.id.back).apply {
            setOnClickListener { listener?.onBackPressed() }
        }
        addInfoMovieDetal()
        openActorAdapter()
    }
    fun setListener(l: ClickListenerDetall) {
        listener = l
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is ClickListenerDetall){
            listener=context
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        listener=null
    }



    //TODO(WS2:4) Create interface ClickListener
    interface ClickListenerDetall {

        fun onBackPressed()

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragment_movies_details.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(data: Movie) =
                fragment_movies_details().apply {
                    arguments = Bundle().apply {
                        putParcelable(ARG_MOVIE, data)

                    }
                }
    }
}