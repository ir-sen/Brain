package com.example.brain.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.example.brain.R
import com.example.brain.databinding.FragmentGameFinishBinding
import com.example.brain.domain.entity.GameResult


class GameFinish : Fragment() {

    private var _binding: FragmentGameFinishBinding? = null
    private val binding: FragmentGameFinishBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishBinding == null")
    private lateinit var gameSettingResult: GameResult



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
       _binding = FragmentGameFinishBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        bindViews()
    }

    private fun setupClickListeners() {

        binding.buttonRetry.setOnClickListener {
            retryGame()
        }
    }

    private fun bindViews() {
        with(binding) {
            emojiResult.setImageResource(getSmileResId())
            tvRequiredAnswers.text = String.format(
                getString(R.string.required_score),
                gameSettingResult.gameSettings.minCountOfRightAnswers
            )
            tvScoreAnswers.text = String.format(
                getString(R.string.score_answers),
                gameSettingResult.countOfRightAnswers
            )
            tvRequiredPercentage.text = String.format(
                getString(R.string.required_percentage),
                gameSettingResult.gameSettings.minPercentOfRightAnswers
            )
            tvScorePercentage.text = String.format(
                getString(R.string.score_percentage),
                getPresentOfRightAnswers()
            )
        }
    }

    private fun getSmileResId() : Int {
        return if (gameSettingResult.winner) {
            R.drawable.ic_smile
        } else {
            R.drawable.ic_sad
        }
    }

    private fun getPresentOfRightAnswers() = with(gameSettingResult){
        if (countOfQuestion == 0) {
            0
        } else {
            ((countOfRightAnswers / countOfQuestion.toDouble()) * 100).toInt()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun parseArgs() {
        requireArguments().getParcelable<GameResult>(KEY_GAME_RESULT)?.let {
            gameSettingResult = it
        }
    }


    private fun retryGame() {
        findNavController().popBackStack()
    }



    companion object {

        const val KEY_GAME_RESULT = "game_result"

        fun newInstance(result: GameResult) : GameFinish {
            return GameFinish().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_GAME_RESULT, result)
                }
            }
        }
    }
}
