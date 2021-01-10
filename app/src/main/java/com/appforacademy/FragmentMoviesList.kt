package com.appforacademy

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.academy.fundamentals.homework.features.data.Movie
import com.android.academy.fundamentals.homework.features.data.loadMovies
import kotlinx.coroutines.*



class fragment_movies_list : Fragment(),MoviesRVAdapter.OnItemClickListener,ViewMoviesList {

    private var listener:ClickListener? = null

    private var recycler: RecyclerView? = view?.findViewById(R.id.rv_list_movies)

    private var movieList: List<Movie>? = null

    private val movieAdapter = MoviesRVAdapter(this)

    private var presenterMoviesList:PresenterMoviesList?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_list, container, false)


    }

     override fun loadMoviesInList(){
        CoroutineScope(Dispatchers.IO).launch {
            movieList = loadMovies(requireContext())
            addAdapterMoviesByAdapter()
        }
    }
    suspend fun addAdapterMoviesByAdapter()= withContext(Dispatchers.Main) {
        movieAdapter.setData(movieList!!)
        recycler?.adapter=movieAdapter
    }

    fun initViews(view: View){
        recycler = view.findViewById(R.id.rv_list_movies)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenterMoviesList= PresenterMoviesList()
        presenterMoviesList?.attachView(this)

        initViews(view)
        presenterMoviesList?.loadMoviesInListfromPresenter()
       // loadMoviesInList()


    }
    override fun onStart() {
        super.onStart()


    }



    fun setListener(l: ClickListener) {
        listener = l
    }


    interface ClickListener {
        fun openMovieDetall(data: Movie)


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is ClickListener){
            listener=context
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenterMoviesList?.detachView()
        listener=null
    }

    companion object {


    }

    override fun openMoviesDetallNew(data: Movie){
        listener?.openMovieDetall(data)
    }
    override fun onItemClick(data: Movie) {
        presenterMoviesList?.openMoviesDetallNew(data)
    }
}