package com.example.brain.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class GameResult(
    val winner: Boolean,
    val countOfRightAnswers: Int,
    val countOfQuestion: Int,
    val gameSettings: GameSettings
) : Parcelable {
    val countOfRightAnswersString: String
        get() = countOfRightAnswers.toString()

}