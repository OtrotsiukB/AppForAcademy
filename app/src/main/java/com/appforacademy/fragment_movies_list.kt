package com.appforacademy

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.RecyclerView
import com.android.academy.fundamentals.homework.features.data.Movie
import com.android.academy.fundamentals.homework.features.data.loadMovies
import kotlinx.coroutines.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_movies_list.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragment_movies_list : Fragment(),List_RecyclerViewAdapter.OnItemClickListener {

    private var listener:ClickListener? = null

    private var recycler: RecyclerView? = view?.findViewById(R.id.rv_list_movies)

    private var movieList: List<Movie>? = null
    private var job: Job? = null
    private val movieAdapter = List_RecyclerViewAdapter(this)




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

      fun loadMoviesInList(){
        CoroutineScope(Dispatchers.IO).launch {
            movieList = loadMovies(requireContext())
            addAdapterMoviesByAdapter()
        }
    }
    suspend fun addAdapterMoviesByAdapter()= withContext(Dispatchers.Main) {
        movieAdapter.setData(movieList!!)
        recycler?.adapter=movieAdapter
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler = view.findViewById(R.id.rv_list_movies)
        loadMoviesInList()


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
        listener=null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragment_movies_list.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fragment_movies_list().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemClick(data: Movie) {
        listener?.openMovieDetall(data)
    }
}