package com.example.brain.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.brain.R
import com.example.brain.data.GameRepositoryImpl
import com.example.brain.domain.entity.GameResult
import com.example.brain.domain.entity.GameSettings
import com.example.brain.domain.entity.Level
import com.example.brain.domain.entity.Question
import com.example.brain.domain.usecases.GenerateQuestionUseCase
import com.example.brain.domain.usecases.GetGameSettingUseCase

class GameViewModel(
    private val level: Level,
    private val application: Application
): ViewModel() {

    private lateinit var gameSettings: GameSettings



    private val repository = GameRepositoryImpl
    // this is listen real time objects
    // show timer in view
    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime

    // show questing in view
    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private val _percentOfRightAnswer = MutableLiveData<Int>()
    val percentOfRightAnswer: LiveData<Int>
        get() = _percentOfRightAnswer

    private val _progressAnswers = MutableLiveData<String>()
    val progressAnswers: LiveData<String>
        get() = _progressAnswers

    // view bar and change color listener
    private val _enoughCount = MutableLiveData<Boolean>()
    val enoughCount: LiveData<Boolean>
        get() = _enoughCount

    private val _enoughPercent = MutableLiveData<Boolean>()
    val enoughPercent: LiveData<Boolean>
        get() = _enoughPercent

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult


    private var timer: CountDownTimer? = null

    // link our useCase
    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)
    private val getGameSettingUseCase = GetGameSettingUseCase(repository)

    private var countOfRightAnswers = 0
    private var countOfQuestions = 0

    init {
        startGame()
    }


    private fun startGame() {
        getGameSetting()
        startTimer()
        generateQuestion()
        updateProgress()
    }

    fun choseAnswer(number: Int) {
        checkAnswer(number)
        updateProgress()
        generateQuestion()
    }

    private fun updateProgress() {
        val percentRightAnswers = calculatePercentOfRightAnswers()
        _percentOfRightAnswer.value = percentRightAnswers
        _progressAnswers.value = String.format(
            application.resources.getString(R.string.progress_answers),
            countOfRightAnswers,
            gameSettings.minCountOfRightAnswers
        )
        _enoughCount.value = countOfRightAnswers >= gameSettings.minCountOfRightAnswers
        _enoughPercent.value = percentRightAnswers >= gameSettings.minPercentOfRightAnswers
    }

    private fun calculatePercentOfRightAnswers(): Int {
        if (countOfQuestions == 0) {
            return 0
        }
        return ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
    }

    private fun checkAnswer(number: Int) {
        val rightAnswer = _question.value?.rightAnswer
        if (number == rightAnswer) {
            countOfRightAnswers++
        }
        countOfQuestions++
    }

    private fun getGameSetting() {
        this.gameSettings = getGameSettingUseCase(level)
        _minPercent.value = gameSettings.minPercentOfRightAnswers
    }

    private fun generateQuestion() {
        _question.value = generateQuestionUseCase(gameSettings.maxSumValue)
    }

    private fun startTimer() {
        timer = object : CountDownTimer(
            // how long time work
            gameSettings.gameTimeInSeconds * MILLIS_IN_SECONDS,
            MILLIS_IN_SECONDS

        ){
            // every tick
            override fun onTick(millisUntilFinish: Long) {
                _formattedTime.value = formatTime(millisUntilFinish)
            }

            override fun onFinish() {
                finishGame()
            }

        }
        timer?.start()
    }

    private fun formatTime(millisUntilFinish: Long) : String {
        val second = millisUntilFinish / MILLIS_IN_SECONDS
        val minutes = second / SECOND_IN_MINUTES
        val leftSeconds = second - (minutes * SECOND_IN_MINUTES)
        return String.format("%02d:%02d", minutes, leftSeconds)
    }

    private fun finishGame() {
        _gameResult.value = GameResult(
            enoughCount.value == true && enoughPercent.value == true,
            countOfRightAnswers,
            countOfQuestions,
            gameSettings,
        )
    }

    // in finish ViewModel
    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    companion object {
        const val MILLIS_IN_SECONDS = 1000L
        const val SECOND_IN_MINUTES = 60
    }


}