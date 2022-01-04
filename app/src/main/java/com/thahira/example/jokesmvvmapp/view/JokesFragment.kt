package com.thahira.example.jokesmvvmapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thahira.example.jokesmvvmapp.adapter.JokesRecyclerViewAdapter
import com.thahira.example.jokesmvvmapp.databinding.FragmentJokesBinding
import com.thahira.example.jokesmvvmapp.model.Jokes
import com.thahira.example.jokesmvvmapp.utils.UIState
import com.thahira.example.jokesmvvmapp.viewmodel.JokeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.System.load


class JokesFragment : Fragment() {

    private lateinit var binding: FragmentJokesBinding

    private val viewModel by viewModel<JokeViewModel>()

    private var jokesRecyclerViewAdapter = JokesRecyclerViewAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel.allJokesObserver.observe(viewLifecycleOwner,::handleResult)
        binding = FragmentJokesBinding.inflate(inflater,container,false)

        binding.jokesRecycler.apply{
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            adapter= jokesRecyclerViewAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                   val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val totalItemCount = layoutManager.itemCount
                    val lastVisible = layoutManager.findLastVisibleItemPosition()
                    val endHasBeenReached = lastVisible + 5 >=totalItemCount

                    if(totalItemCount>0 && endHasBeenReached){
                        viewModel.getJokes()
                    }

                }
            })
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getJokes()
    }

    private fun handleResult(uiState: UIState?) {

        when(uiState){
            is UIState.LOADING ->{
                Toast.makeText(context,"LOADING", Toast.LENGTH_LONG).show()
            }
            is UIState.SUCCESS ->{getnewJokes(uiState.jokes) }
            is UIState.SINGLE_SUCCESS ->{ }
            is UIState.ERROR ->{ Log.e("All Jokes",uiState.error.localizedMessage)}
        }

    }

    private fun getnewJokes(jokes: List<Jokes>) {
        jokesRecyclerViewAdapter.loadJokes(jokes)
    }

    companion object {
        @JvmStatic
        fun newInstance() = JokesFragment()
    }
}