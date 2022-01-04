package com.thahira.example.jokesmvvmapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.thahira.example.jokesmvvmapp.R
import com.thahira.example.jokesmvvmapp.model.Jokes
import com.thahira.example.jokesmvvmapp.model.Value
import com.thahira.example.jokesmvvmapp.viewmodel.JokeViewModel

class JokesRecyclerViewAdapter(
    private val values: MutableList<Value> = mutableListOf()
): RecyclerView.Adapter<JokeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        val jokeView = LayoutInflater.from(parent.context).inflate(
            R.layout.joke_layout,
            parent,
            false
        )
        return JokeViewHolder(jokeView)
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
            val item = values[position]
        holder.idJoke.text = item.id.toString()
        holder.textJoke.text = item.joke
    }

    override fun getItemCount(): Int = values.size

    fun loadJokes(jokes: List<Jokes>){
        values.addAll(jokes[0].value)
        notifyDataSetChanged()
        Log.e("Adapter","Data loaded")
    }
}

class JokeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    var idJoke: TextView = itemView.findViewById(R.id.joke_id)
    var textJoke: TextView = itemView.findViewById(R.id.joke_text)
}
