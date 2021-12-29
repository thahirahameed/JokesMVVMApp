package com.thahira.example.jokesmvvmapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.thahira.example.jokesmvvmapp.R
import com.thahira.example.jokesmvvmapp.databinding.FragmentFirstBinding
import com.thahira.example.jokesmvvmapp.model.SingleJoke
import com.thahira.example.jokesmvvmapp.utils.UIState
import com.thahira.example.jokesmvvmapp.viewmodel.JokeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class FirstFragment() : Fragment() {

    private val binding : FragmentFirstBinding by lazy {
        FragmentFirstBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModel<JokeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.randomJokeObserver.observe(viewLifecycleOwner,::handleResult)

        binding.randomJoke.setOnClickListener(){
            viewModel.getRandomJoke()
        }

        binding.endlessJokes.setOnClickListener(){
            switchFragments(parentFragmentManager,JokesFragment.newInstance())

        }

        binding.heroJoke.setOnClickListener(){
            switchFragments(parentFragmentManager,HeroFragment.newInstance())
        }

        return binding.root
    }

    private fun switchFragments(manager: FragmentManager, fragment: Fragment) {

        manager.beginTransaction()
            .replace(R.id.fragment_container,fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun handleResult(uiState: UIState?) {

        when(uiState){
            is UIState.LOADING ->{
                Toast.makeText(context,"LOADING",Toast.LENGTH_LONG).show()
            }
            is UIState.SUCCESS ->{ }
            is UIState.SINGLE_SUCCESS ->{ getRandomJoke(uiState.joke)}
            is UIState.ERROR ->{ Log.e("Random Joke",uiState.error.localizedMessage)}
        }

    }

    private fun getRandomJoke(joke: SingleJoke)
    {
        Toast.makeText(context,joke.value.joke,Toast.LENGTH_LONG).show()
        //binding.randomJokeText.text = joke.value.joke
    }

    companion object {

        @JvmStatic
        fun newInstance() = FirstFragment()
    }
}