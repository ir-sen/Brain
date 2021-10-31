package com.example.brain.domain.usecases

import com.example.brain.domain.entity.GameSettings
import com.example.brain.domain.entity.Level
import com.example.brain.domain.repository.GameRepository

class GetGameSettingUseCase(
    private val repository: GameRepository
) {

    operator fun invoke(level: Level): GameSettings {
        return repository.getGameSettings(level)
    }
}