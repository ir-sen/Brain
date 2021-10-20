package com.example.brain.domain.usecases

import com.example.brain.domain.entity.Question
import com.example.brain.domain.repository.GameRepository

class GenerateQuestionUseCase(
    private val repository: GameRepository
) {
    // allow get like method value.GenerateQuestionUseCase() or .invoke
    operator fun invoke(maxSumValue: Int): Question {
        return repository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }


    companion object{
        private const val COUNT_OF_OPTIONS = 6
    }
}