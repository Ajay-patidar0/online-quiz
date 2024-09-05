package com.example.quizonline

import java.sql.Time

data class QuizModel(
    val id: String,
    val title: String,
    val subtitle: String,
    val time: String,
    val qslist: List<QuestionModel>,
){
    constructor(): this("", "", "", "", emptyList())
}

data class QuestionModel(
    val question:String,
    val options:List<String>,
    val ans: String,
){
    constructor(): this("", emptyList(), "")
}
