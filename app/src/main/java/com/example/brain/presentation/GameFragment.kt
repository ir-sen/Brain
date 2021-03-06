package com.example.brain.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.brain.R
import com.example.brain.databinding.FragmentGameBinding
import com.example.brain.domain.entity.GameResult
import com.example.brain.domain.entity.GameSettings
import com.example.brain.domain.entity.Level


class GameFragment : Fragment() {


    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

    // Second method get args
    private val args by navArgs<GameFragmentArgs>()




    private val viewModelFactory by lazy {
        // First method get level (arguments)
 //        val args = GameFragmentArgs.fromBundle(requireArguments())
        GameViewModelFactory(args.level, requireActivity().application)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // to unsubscribe in time and work with LiveData
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        observeViewModel()
    }



    private fun observeViewModel() {
        viewModel.gameResult.observe(viewLifecycleOwner) {
            launchFinishFragment(it)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



    private fun launchFinishFragment(result: GameResult) {
        findNavController().navigate(
            GameFragmentDirections.actionGameFragmentToGameFinish(result)
        )

    }


}


