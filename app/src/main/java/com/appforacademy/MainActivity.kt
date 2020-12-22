package com.appforacademy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.academy.fundamentals.homework.features.data.Movie

class MainActivity : AppCompatActivity(),fragment_movies_list.ClickListener,fragment_movies_details.ClickListenerDetall {

    private var fragment_movies_detal = fragment_movies_details().apply { setListener(this@MainActivity) }
    private var fragment_movies_list = fragment_movies_list().apply { setListener(this@MainActivity) }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //val intent = Intent(this,MovieDetailsActivity::class.java)
       // startActivity(intent)
      /*  supportFragmentManager.beginTransaction().apply {
            add(R.id.Fragment_container_Main,fragment_movies_detal)
            commit()
        }
        */
                 if(savedInstanceState==null) {
            supportFragmentManager.beginTransaction().apply {

                add(R.id.Fragment_container_Main, fragment_movies_list)

                commit()

            }
        }

    }
    override fun openMovieDetall(data: Movie){
        supportFragmentManager.beginTransaction().apply {
            addToBackStack(null)
            add(R.id.Fragment_container_Main,fragment_movies_details.newInstance(data).apply { setListener(this@MainActivity) })//fragment_movies_detal
            commit()

        }
     }




}