package com.example.brain.domain.repository

import com.example.brain.domain.entity.GameSettings
import com.example.brain.domain.entity.Level
import com.example.brain.domain.entity.Question

interface GameRepository {

    fun generateQuestion(
        maxSumValue: Int,
        countOfOptions: Int
    ): Question

    fun getGameSettings(level: Level) : GameSettings
}