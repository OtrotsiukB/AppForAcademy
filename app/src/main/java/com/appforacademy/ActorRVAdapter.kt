package com.appforacademy
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.academy.fundamentals.homework.features.data.Actor
import com.android.academy.fundamentals.homework.features.data.Movie
import com.bumptech.glide.Glide

class ActorRVAdapter:RecyclerView.Adapter<ActorRVAdapter.EmptyViewHolder>() {

    private var actorList = listOf<Actor>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmptyViewHolder {
        val itemView =LayoutInflater.from(parent.context).inflate(
            R.layout.actorsitem,
            parent,
            false
        )
        return EmptyViewHolder(itemView)

    }
    fun setData(data: List<Actor>) {
        this.actorList = data
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


        fun onBind(actorList: Actor) {
            nameActor.text=actorList.name
            Glide.with(itemView.context)
                    //.load(R.drawable.movie6)
                    .load(actorList.picture)
                    .into(ic_actor)
           // ic_actor.setImageResource(actorList.ic_actor)


        }
    }



}