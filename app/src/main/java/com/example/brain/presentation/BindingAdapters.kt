package com.example.brain.presentation

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.brain.R
import com.example.brain.domain.entity.GameResult

@BindingAdapter("requiredAnswers")
fun bindRequiredAnswers(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.required_score),
        count
    )
}

@BindingAdapter("score")
fun bindScore(textView: TextView, score: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.score_answers),
        score
    )
}

@BindingAdapter("requiredPer")
fun bindRequired(textView: TextView, required: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.required_percentage),
        required
    )
}

@BindingAdapter("scorePercentage")
fun bindScorePercentage(textView: TextView, gameResult: GameResult) {
    textView.text = String.format(
        textView.context.getString(R.string.score_percentage),
        getPresentOfRightAnswers(gameResult)
    )
}



private fun getPresentOfRightAnswers(gameResult: GameResult) = with(gameResult){
    if (countOfQuestion == 0) {
        0
    } else {
        ((countOfRightAnswers / countOfQuestion.toDouble()) * 100).toInt()
    }
}

@BindingAdapter("resultEmoji")
fun bindResultEmoji(imageView: ImageView, winner: Boolean) {
    imageView.setImageResource(getSmileResId(winner))
}

private fun getSmileResId(winner: Boolean) : Int {
    return if (winner) {
        R.drawable.ic_smile
    } else {
        R.drawable.ic_sad
    }
}


