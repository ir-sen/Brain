package com.example.brain.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.example.brain.R
import com.example.brain.databinding.FragmentGameFinishBinding
import com.example.brain.domain.entity.GameResult


class GameFinish : Fragment() {

    private var _binding: FragmentGameFinishBinding? = null
    private val binding: FragmentGameFinishBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishBinding == null")
    private lateinit var gameSettingResult: GameResult

    ;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       _binding = FragmentGameFinishBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Get link activity and dispatcher add to click listener back press
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                retryGame()
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun parseArgs() {
        gameSettingResult = requireArguments().getSerializable(KEY_GAME_RESULT) as GameResult
    }


    private fun retryGame() {
        requireActivity().supportFragmentManager.popBackStack(ChoseLevel.NAME, 0)
    }



    companion object {

        private const val KEY_GAME_RESULT = "game_result"

        fun newInstance(result: GameResult) : GameFinish {
            return GameFinish().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_GAME_RESULT, result)
                }
            }
        }
    }
}
