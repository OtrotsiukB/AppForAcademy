package com.appforacademy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private var fragment_movies_detal = fragment_movies_details()
    private var fragment_movies_list = fragment_movies_list()

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
        supportFragmentManager.beginTransaction().apply {
            add(R.id.Fragment_container_Main,fragment_movies_list)
            commit()

        }

    }
}