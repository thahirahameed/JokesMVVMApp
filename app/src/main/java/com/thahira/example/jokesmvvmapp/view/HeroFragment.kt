package com.thahira.example.jokesmvvmapp.view

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import com.thahira.example.jokesmvvmapp.R
import com.thahira.example.jokesmvvmapp.databinding.FragmentHeroBinding
import com.thahira.example.jokesmvvmapp.model.SingleJoke
import com.thahira.example.jokesmvvmapp.utils.UIState
import com.thahira.example.jokesmvvmapp.viewmodel.JokeViewModel
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.viewModel

class HeroFragment : Fragment() {

    private val viewModel by viewModel<JokeViewModel>()
    private lateinit var binding : FragmentHeroBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.heroJokeObserver.observe(viewLifecycleOwner,::handleResult)
        binding = FragmentHeroBinding.inflate(inflater,container,false)

        binding.getHeroJokeButton.setOnClickListener{
            var firstName : String = binding.firstHero.text.toString()
            var lastName : String = binding.lastHero.text.toString()
            if(firstName!=null||lastName!=null)
            {
                getNewHero(firstName,lastName)
            }
            else
            {
                Toast.makeText(requireContext(),
                    "Please enter any valid name",
                Toast.LENGTH_LONG
                ).show()
            }
        }
        return binding.root
    }

    private fun getNewHero(firstName: String, lastName: String) {
        viewModel.getHeroJoke(firstName,lastName)
    }

    private fun handleResult(uiState: UIState?) {

        when(uiState){
            is UIState.LOADING ->{
                Toast.makeText(context,"LOADING",Toast.LENGTH_LONG).show()
            }
            is UIState.SUCCESS ->{ }
            is UIState.SINGLE_SUCCESS ->{getJoke(uiState.joke)}
            is UIState.ERROR ->{ Log.e("Hero Joke",uiState.error.localizedMessage)}
        }
    }

    private fun getJoke(joke: SingleJoke) {
        binding.newHeroJoke.text = joke.value.joke
    }

    companion object {

        @JvmStatic
        fun newInstance() = HeroFragment()
    }
}