package com.example.quizonline

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quizonline.databinding.ActivityQuizBinding
import com.example.quizonline.databinding.ScoreDialogBinding

class QuizActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        var qsmodelList:List<QuestionModel> = listOf()
        var time:String=""

    }
    
    lateinit var binding:ActivityQuizBinding
    var currentQsindex=0
    var selectedAns=""
    var score=0


    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityQuizBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.apply {
            btn0.setOnClickListener(this@QuizActivity)
            btn1.setOnClickListener(this@QuizActivity)
            btn2.setOnClickListener(this@QuizActivity)
            btn3.setOnClickListener(this@QuizActivity)
            nxtBtn.setOnClickListener(this@QuizActivity)
        }

        loadQuestions()
        startTimer()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
    }

    private fun startTimer() {
        val totalTimeinMillis= time.toInt()*60*1000L
        object : CountDownTimer(totalTimeinMillis,1000L){
            override fun onTick(millisUntilFinished: Long) {
                 val seconds= (millisUntilFinished/1000)
                val minutes= seconds/60
                val remainingSeconds= seconds %60
                binding.timerIndicatorTextview.text=String.format("%02d:%02d",minutes,remainingSeconds)
            }
            override fun onFinish() {
                    // finish the quiz
            }
        }.start()
    }

    private fun loadQuestions(){
        if(currentQsindex== qsmodelList.size){
            finsihQuiz()
            return
        }
            binding.apply {
                qsIndicatorTextview.text="Question ${currentQsindex+1}/${qsmodelList.size}"
                quizProgressBar.progress= (currentQsindex.toFloat()/ qsmodelList.size.toFloat()*100).toInt()
                qsTextView.text= qsmodelList[currentQsindex].question
                btn0.text=qsmodelList[currentQsindex].options[0]
                btn1.text=qsmodelList[currentQsindex].options[1]
                btn2.text=qsmodelList[currentQsindex].options[2]
                btn3.text=qsmodelList[currentQsindex].options[3]
            }
   }

    override fun onClick(view: View?) {
       binding.apply {
            btn0.setBackgroundColor(getColor(R.color.gray))
            btn1.setBackgroundColor(getColor(R.color.gray))
            btn2.setBackgroundColor(getColor(R.color.gray))
            btn3.setBackgroundColor(getColor(R.color.gray))
        }
            val clickedBtn= view as Button
        if(clickedBtn.id==R.id.nxt_btn){
            if(selectedAns==qsmodelList[currentQsindex].ans){
                score++
                Log.i("score",score.toString())
            }
            currentQsindex++
            loadQuestions()
        }else{
            //option button is clicked
            selectedAns=clickedBtn.text.toString()
            clickedBtn.setBackgroundColor(getColor(R.color.orange))

        }

    }

    private fun finsihQuiz(){
        val totalqs= qsmodelList.size
        val percentage= ((score.toFloat()/totalqs.toFloat())*100).toInt()
        val dialogBinding= ScoreDialogBinding.inflate(layoutInflater)
        dialogBinding.apply {
            scoreProgressBar.progress= percentage
            scoreProgressText.text="$percentage%"
            if(percentage>60){
                scoreTitle.text="Congrats you have passed the quiz"
                scoreTitle.setTextColor(getColor(R.color.blue))


            }else{
                scoreTitle.text="Sorry you have failed the quiz"
                scoreTitle.setTextColor(getColor(R.color.red))
            }
            scoreSubtitle.text="You scored $score out of $totalqs"
            finishBtn.setOnClickListener{
                finish()

            }
        }
        AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setCancelable(false)
            .show()
    }

}