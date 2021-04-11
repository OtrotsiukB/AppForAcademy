package com.appforacademy
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ActorRVAdapter:RecyclerView.Adapter<ActorRVAdapter.EmptyViewHolder>() {

    private var actorList = listOf<DataActors>(DataActors(R.drawable.movie,"Robert Downey Jr."),
                                                DataActors(R.drawable.movie1,"Chris Evans"),
                                                DataActors(R.drawable.movie2,"Mark Ruffalo"),
                                                DataActors(R.drawable.movie3,"Chris Hemsworth")
    )
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmptyViewHolder {
        val itemView =LayoutInflater.from(parent.context).inflate(
            R.layout.actorsitem,
            parent,
            false
        )
        return EmptyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: EmptyViewHolder, position: Int) {
        holder.onBind(actorList[position])
    }

    override fun getItemCount():Int {
      return  actorList.count()
    }

    class EmptyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){



        var nameActor = itemView.findViewById<TextView>(R.id.actortext)
        var ic_actor = itemView.findViewById<ImageView>(R.id.actorRV)


        fun onBind(actorList: DataActors) {
            nameActor.text=actorList.nameActor
            ic_actor.setImageResource(actorList.ic_actor)


        }
    }



}